package com.kosa.pos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kosa.pos.dbconnection.DBConnection;
import com.kosa.pos.dto.Menu;
import com.kosa.pos.dto.MenuDetail;
import com.kosa.pos.dto.MenuGetRankNReview;
import com.kosa.pos.dto.MenuRanking;
import com.kosa.pos.dto.MenuStatsInfo;
import com.kosa.pos.dto.Review;

import oracle.jdbc.OracleTypes;

public class MenuDAOImpl implements MenuDAO {

	private Connection connection = DBConnection.getConnection();
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private CallableStatement cstmt = null;
	private ResultSet rs = null;

	public List<Menu> findall() {
		List<Menu> menuList = new ArrayList<>(); // 메뉴 객체를 담을 리스트
		try {
			// 쿼리문 작성
			String sql = "SELECT * FROM menu";

			// PreparedStatement 객체 생성 후 쿼리 실행
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			// 결과 처리
			while (rs.next()) {
				// 각 레코드마다 Menu 객체를 생성하고 결과를 설정한 후 리스트에 추가
				Menu menu = new Menu();
				menu.setMenu_id(rs.getInt("MENU_ID"));
				menu.setName(rs.getString("NAME"));
				menu.setPrice(rs.getInt("PRICE"));
				menu.setCategory(rs.getString("CATEGORY"));
				menu.setMenu_desc(rs.getString("MENU_DESC"));
				menu.setMenu_path(rs.getString("MENU_PATH"));
				menuList.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menuList;
	}

	@Override
	public Optional<MenuDetail> findById(int menuId) {

//		int menuID = 1;
		String runSP = "{ call menu_package.get_menu_detail(?,?,?,?,?,?,?) }";

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setInt(1, menuId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // 메뉴 디테일
			callableStatement.registerOutParameter(3, OracleTypes.NUMBER); // 주문 받은 횟수
			callableStatement.registerOutParameter(4, OracleTypes.NUMBER); // 리뷰 평점
			callableStatement.registerOutParameter(5, OracleTypes.CURSOR); // 리뷰 리스트 최신순
			callableStatement.registerOutParameter(6, OracleTypes.CURSOR); // 리뷰 리스트 평점순
			callableStatement.registerOutParameter(7, OracleTypes.CURSOR); // 리뷰 리스트 오래된순
			callableStatement.execute();

			ResultSet menuRec = (ResultSet) callableStatement.getObject(2);
			int menuOrderCount = callableStatement.getInt(3);
			float menuReviewAvgScore = callableStatement.getFloat(4);
			ResultSet orderByReviewDateDesc = (ResultSet) callableStatement.getObject(5);
			ResultSet orderByRatingDesc = (ResultSet) callableStatement.getObject(6);
			ResultSet orderByReviewDateAsc = (ResultSet) callableStatement.getObject(7);

			MenuDetail menuDetail = new MenuDetail();
			// 세부 메뉴 정보
			if (menuRec.next()) {
				Menu menu = new Menu();
				menu.setMenu_id(menuRec.getInt("MENU_ID"));
				menu.setName(menuRec.getString("NAME"));
				menu.setPrice(menuRec.getInt("PRICE"));
				menu.setCategory(menuRec.getString("CATEGORY"));
				menu.setMenu_desc(menuRec.getString("MENU_DESC"));
				menu.setMenu_path(menuRec.getString("MENU_PATH"));
				menuDetail.setMenu(menu);
			}

			// 주문 횟수, 리뷰 평점
			menuDetail.setCount(menuOrderCount);
			menuDetail.setAvgScore(menuReviewAvgScore);

			// 리뷰 최신순 리스트
			List<Review> orderByReviewDateDescList = new ArrayList<>();
			while (orderByReviewDateDesc.next()) {
				Review review = new Review();
				review.setTitle(orderByReviewDateDesc.getString("TITLE"));
				review.setContent(orderByReviewDateDesc.getString("CONTENT"));
				review.setRating(orderByReviewDateDesc.getInt("RATING"));
				review.setReviewDate(orderByReviewDateDesc.getDate("REVIEW_DATE"));
				orderByReviewDateDescList.add(review);
			}
			menuDetail.setOrderByReviewDateDescList(orderByReviewDateDescList);

			// 리뷰 평점순 리스트
			List<Review> orderByRatingDescList = new ArrayList<>();
			while (orderByRatingDesc.next()) {
				Review review = new Review();
				review.setTitle(orderByRatingDesc.getString("TITLE"));
				review.setContent(orderByRatingDesc.getString("CONTENT"));
				review.setRating(orderByRatingDesc.getInt("RATING"));
				review.setReviewDate(orderByRatingDesc.getDate("REVIEW_DATE"));
				orderByRatingDescList.add(review);
			}
			menuDetail.setOrderByRatingDescList(orderByRatingDescList);

			// 리뷰 오래된순 리스트
			List<Review> orderByReviewDateAscList = new ArrayList<>();
			while (orderByReviewDateAsc.next()) {
				Review review = new Review();
				review.setTitle(orderByReviewDateAsc.getString("TITLE"));
				review.setContent(orderByReviewDateAsc.getString("CONTENT"));
				review.setRating(orderByReviewDateAsc.getInt("RATING"));
				review.setReviewDate(orderByReviewDateAsc.getDate("REVIEW_DATE"));
				orderByReviewDateAscList.add(review);
			}
			menuDetail.setOrderByReviewDateAscList(orderByReviewDateAscList);

			return Optional.of(menuDetail);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Menu findByName(String menuName) {
		Menu menu = null;

		try {
			cstmt = connection.prepareCall("{call menu_package.get_menu_detail(?,?)}");
			cstmt.setString(1, menuName);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();

			rs = (ResultSet) cstmt.getObject(2);

			if (rs.next()) {
				menu = new Menu();
				menu.setMenu_id(rs.getInt("menu_id"));
				menu.setName(rs.getString("name"));
				menu.setPrice(rs.getInt("price"));
				menu.setCategory(rs.getString("category"));
				menu.setMenu_desc(rs.getString("menu_desc"));
				menu.setMenu_path(rs.getString("menu_path"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLState: " + se.getSQLState());
			System.out.println("Error Code: " + se.getErrorCode());
			System.out.println("Message: " + se.getMessage());
			System.out.println("menu_package.findByName에서 에러 발생");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (cstmt != null)
					cstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return menu;
	}

	@Override
	public Optional<MenuStatsInfo> findOrderCountByName(String name) {
//		int menuID = 1;
		String runSP = "{ call menu_stats_info.GET_MENU_GRAPHSEARCH_DATA(?,?) }";

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setString(1, name);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // 특정 메뉴의 최근 일주일간의 주문 받은 횟수

			callableStatement.execute();

			ResultSet orderCount = (ResultSet) callableStatement.getObject(2);

			MenuStatsInfo menuStatsInfo = new MenuStatsInfo();
			int index = 0;
			while (orderCount.next()) {
				menuStatsInfo.getDay()[index] = orderCount.getDate("OrderDate").toString();
				menuStatsInfo.getValues()[index] = orderCount.getDouble("MENU_QUANTITY");
				index++;
			}
			return Optional.of(menuStatsInfo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return Optional.empty();
	}
	
	@Override
	public Optional<MenuStatsInfo> findOrderCountByName() {
//		int menuID = 1;
		String runSP = "{ call menu_stats_info.GET_GRAPH_DATA (?) }";

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR); // 특정 메뉴의 최근 일주일간의 주문 받은 횟수
			callableStatement.execute();

			rs = (ResultSet) callableStatement.getObject(1);

			MenuStatsInfo menuStatsInfo = new MenuStatsInfo();
			int index = 0;
			while (rs.next()) {
				menuStatsInfo.getDay()[index] = rs.getDate("OrderDate").toString();
				menuStatsInfo.getValues()[index] = rs.getDouble("TotalOrders");
				index++;
			}
			return Optional.of(menuStatsInfo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return Optional.empty();
	}

	@Override
	public List<MenuGetRankNReview> findBestMenuAll(String name) {
		String runSP = "{ call menu_stats_info.get_menu_ranking(?,?) }";

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setString(1, name);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // 인기 메뉴
			callableStatement.execute();

			ResultSet menuRank = (ResultSet) callableStatement.getObject(2);

			List<MenuGetRankNReview> menuRankList = new ArrayList<>();
			while (menuRank.next()) {
				MenuGetRankNReview menuDetail = new MenuGetRankNReview();
				menuDetail.setAvgReview(menuRank.getDouble("AVG_RATING"));
				menuDetail.setTotalOrders(menuRank.getInt("TOTAL_ORDERS"));
				menuDetail.setMenuName(menuRank.getString("MENU_NAME"));
				menuDetail.setCategory(menuRank.getString("CATEGORY"));
				menuRankList.add(menuDetail);

			}
			return menuRankList;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Menu> findByCategory(String category) {
		List<Menu> menuList = new ArrayList<>(); // 메뉴 객체를 담을 리스트
		try {
			// 쿼리문 작성
			String sql = "{ call menu_package.find_category(?, ?) }";

			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, category);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // 메뉴의 모든 데이터
			callableStatement.execute();

			ResultSet menubycategory = (ResultSet) callableStatement.getObject(2);
			while (menubycategory.next()) {
				Menu menu = new Menu();
				menu.setMenu_id(menubycategory.getInt("MENU_ID"));
				menu.setName(menubycategory.getString("NAME"));
				menu.setPrice(menubycategory.getInt("PRICE"));
				menu.setCategory(menubycategory.getString("CATEGORY"));
				menu.setMenu_desc(menubycategory.getString("MENU_DESC"));
				menu.setMenu_path(menubycategory.getString("MENU_PATH"));
				menuList.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menuList;
	}

	@Override
	public List<Menu> findAll(String name) {
		String runSP = "{ call menu_package.get_menu_list(?,?) }";

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setString(1, name);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR); // 특정 메뉴의 최근 일주일간의 주문 받은 횟수

			callableStatement.execute();

			ResultSet menuList = (ResultSet) callableStatement.getObject(2);

			List<Menu> list = new ArrayList<>();
			while (menuList.next()) {
				Menu menu = new Menu();
				menu.setMenu_id(menuList.getInt("MENU_ID"));
				menu.setCategory(menuList.getString("CATEGORY"));
				menu.setName(menuList.getString("NAME"));
				list.add(menu);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void insertMenu(Menu menu) {
		String runSP = "{ call menu_package.insert_menu(?,?,?,?,?) }";
		String name = menu.getName();
		String category = menu.getCategory();
		int price = menu.getPrice();
		String menuDesc = menu.getMenu_desc();
		String menuPath = menu.getMenu_path();

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setString(1, name);
			callableStatement.setInt(2, price);
			callableStatement.setString(3, category);
			callableStatement.setString(4, menuDesc);
			callableStatement.setString(5, menuPath);

			callableStatement.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void deleteMenu(int menuId) {
		String runSP = "{ call menu_package.delete_menu(?) }";

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setInt(1, menuId);

			callableStatement.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<MenuRanking> getMenuRanking() {
		List<MenuRanking> menuRankingList = new ArrayList<>();
		ResultSet resultSet = null;
		try {
			String sql = "{ call menu_package.GET_MENU_RANKING_TOP3(?) }";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.execute();
			resultSet = (ResultSet) callableStatement.getObject(1);
			while (resultSet.next()) {
				MenuRanking menuRanking = new MenuRanking();
				menuRanking.setMenuName(resultSet.getString("MENU_NAME"));
				menuRanking.setTotal_order(resultSet.getInt("TOTAL_ORDERS"));
				menuRanking.setTotal_percentage(resultSet.getDouble("ORDER_PERCENTAGE"));
				menuRankingList.add(menuRanking);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// Add connection closing if needed
		}
		return menuRankingList;
	}

	@Override
	public int getTotalOrderWithoutDrink() {
		int totalqawithoutdrink = 0;
		CallableStatement cs = null;
		try {
			String sql = "{ call menu_package.Total_Quantity_WithoutDrink(?) }";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
			callableStatement.execute();

			totalqawithoutdrink = callableStatement.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// Add connection closing if needed
		}
		return totalqawithoutdrink;
	}

	// orderId를 받아서 order_detail에서 menuId List 반환
	public List<Integer> fetchMenuIdByOrderId(int orderId) {
		List<Integer> menuIds = new ArrayList<>();
		try {
			cstmt = connection.prepareCall("{ call menu_package.get_menu_ids_by_order_id(?, ?) }");

			cstmt.setInt(1, orderId);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();

			rs = (ResultSet) cstmt.getObject(2);
			while (rs.next()) {
				menuIds.add(rs.getInt("menu_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("fetchMenuIdByOrderId에서 SQLException 발생: " + e.getMessage());
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return menuIds;
	}

	@Override
	public void updateMenu(Menu menu) {

		String runSP = "{ call menu_package.update_menu(?,?,?,?,?,?) }";
		int menuId = menu.getMenu_id();
		String name = menu.getName();
		String category = menu.getCategory();
		int price = menu.getPrice();
		String menuDesc = menu.getMenu_desc();
		String menuPath = menu.getMenu_path();

		try {
			// PreparedStatement 객체 생성 후 쿼리 실행
			CallableStatement callableStatement = connection.prepareCall(runSP);
			callableStatement.setInt(1, menuId);
			callableStatement.setString(2, name);
			callableStatement.setInt(3, price);
			callableStatement.setString(4, category);
			callableStatement.setString(5, menuDesc);
			callableStatement.setString(6, menuPath);

			callableStatement.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
