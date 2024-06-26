package com.kosa.pos.swing.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.kosa.pos.dao.MenuDAO;
import com.kosa.pos.dao.MenuDAOImpl;
import com.kosa.pos.dao.OrderDAO;
import com.kosa.pos.dto.Menu;
import com.kosa.pos.dto.MenuRanking;
import com.kosa.pos.swing.common.OrderState;
import com.kosa.pos.swing.main.CardLayoutManager;
import com.kosa.pos.swing.main.ContentPaneManager;
import com.kosa.pos.swing.main.Index;
import com.kosa.pos.swing.review.ReviewPanel;
import com.kosa.pos.swing.signUp.CompletePaymentDialog;

public class MenuView extends JPanel {
	MenuDAO menudao = new MenuDAOImpl();
	OrderDAO orderDao = new OrderDAO();
	Index indexFrame;
	/**
	 * @wbp.nonvisual location=418,41
	 */

	/**
	 * Create the panel.
	 */

	JPanel mainpanel = new JPanel();
	static JPanel msbpport = new JPanel(); // menusidebar 를 위해서 위에 생성
	MenuPanel menuPanel;
	private List<MenuPanel> menuPanels = new ArrayList<>(); // 기존에 생성된 MenuPanel을 저장하기 위한 리스트
	private static Map<String, Integer> clickCountManager = new HashMap<>();
	private static Map<String, MenuSidebarPanel> sidebarPanels = new HashMap<>(); // Map to store MenuSidebarPanels by
																					// menu name
	private static List<String> sidebarOrder = new ArrayList<>();
	static JLabel Total = new JLabel(0 + " 원");
	private Index index;
	
	public MenuView(Index index) {
		this.index = index;
		setBackground(new Color(255, 255, 255));

		// 메뉴 패널의 크기를 인덱스 패널과 동일하게 설정

		setPreferredSize(new Dimension(1240, 650));
		setLayout(null);

		// 메뉴 스크롤 부분

		mainpanel.setBounds(6, 92, 735, 552);
		add(mainpanel);
		mainpanel.setLayout(new BorderLayout(0, 0));

		// 카테고리 분류를 통해 메뉴를 여러개 받아온 것을 리스에 저장
		// 리스트에 저장하는 이유는 메뉴 하나는 값 하나당 각 값이 고유하기 때문이다.
		List<Menu> menulist = menudao.findByCategory("돈카츠");
		List<MenuPanel> menuPanels = new ArrayList<>();

		// 메뉴 랭킹 보여주기
		List<MenuRanking> menurank = menudao.getMenuRanking();
		int totalqa = menudao.getTotalOrderWithoutDrink();
		JPanel MenuRankShowPane = new JPanel();

		// MenuPanel을 생성하여 ArrayList에 추가
		for (int i = 0; i < menulist.size(); i++) {
			menuPanel = new MenuPanel(menulist.get(i).getName(), menulist.get(i).getPrice(), msbpport,
					menulist.get(i).getMenu_id(), menulist.get(i).getMenu_path(), index);
			menuPanels.add(menuPanel);
		}

		JPanel viewport = new JPanel();
		if (menulist.size() <= 4) {
			FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);

			viewport.setLayout(flowLayout);
		} else {
			viewport.setLayout(new GridLayout(0, 4, 1, 1)); // 4개의 열
		}

		for (MenuPanel menuPanel : menuPanels) {
			viewport.add(menuPanel);
		}
		viewport.setBackground(new Color(202, 202, 202));
		for (MenuPanel menuPanel : menuPanels) {
			viewport.add(menuPanel);
		}
//		JPanel viewport = new JPanel();
//		viewport.setLayout(new GridLayout(0, 4)); // 4개의 열, 간격은 15픽셀

		JScrollPane scrollPane = new JScrollPane(viewport);
		scrollPane.setPreferredSize(new Dimension(700, 500)); // 스크롤 패널의 크기 제한
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainpanel.add(scrollPane, BorderLayout.CENTER);
		// !메뉴 스크롤 부분

		// 카테고리 버튼
		JButton Category1 = new JButton("돈카츠");
		Category1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadMenuByCategory("돈카츠");
			}
		});
		Category1.setBounds(6, 40, 123, 55);
		add(Category1);

		JButton Category2 = new JButton("마제소바");
		Category2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Category2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadMenuByCategory("마제소바");
			}
		});
		Category2.setBounds(125, 40, 123, 55);
		add(Category2);

		JButton Category3 = new JButton("냉소바");
		Category3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadMenuByCategory("냉소바");
			}
		});
		Category3.setBounds(244, 40, 123, 55);
		add(Category3);

		JButton Category4 = new JButton("온소바");
		Category4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadMenuByCategory("온소바");
			}
		});
		Category4.setBounds(363, 40, 123, 55);
		add(Category4);

		JButton Category6 = new JButton("우동");
		Category6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Category6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadMenuByCategory("우동");
			}
		});
		Category6.setBounds(482, 40, 123, 54);
		add(Category6);

		JButton Category5 = new JButton("음료");
		Category5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadMenuByCategory("음료");
			}
		});
		Category5.setBounds(600, 40, 123, 55);
		add(Category5);
		// !카테고리 버튼

		// sidebar scroll
		JPanel sidepanel = new JPanel();
		sidepanel.setBackground(new Color(255, 255, 255));
		sidepanel.setLayout(new BoxLayout(sidepanel, BoxLayout.Y_AXIS));
		sidepanel.setBounds(753, 92, 190, 420);
		add(sidepanel);
		msbpport.setBackground(new Color(255, 255, 255));
		msbpport.setLayout(new GridLayout(0, 1, 0, 15)); // Set the layout here

//		JPanel msbpport = new JPanel();
//		msbpport.setLayout(new GridLayout(0, 1, 0, 15)); // 4개의 열, 간격은 15픽셀
//		for (int i = 0; i < 10; i++) {
//			MenuSidebarPanel msbp = new MenuSidebarPanel();
//			msbpport.add(msbp);
//		}

		JScrollPane scrollPane_1 = new JScrollPane(msbpport);
		scrollPane_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sidepanel.add(scrollPane_1, BorderLayout.CENTER);
		// !sidebar scroll

		// 장바구니 & 결제 버튼
		// 삭제버튼

		ImageIcon cancel = new ImageIcon("./img/menu/delete2.png");
		Image img = cancel.getImage().getScaledInstance(191, 40, Image.SCALE_SMOOTH);
		ImageIcon cancelimg = new ImageIcon(img);
		JButton deletebtn = new JButton(cancelimg);
		deletebtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetSidebar(msbpport, Total, clickCountManager, MenuPanel.menuTotalPriceMap);
			}
		});
		deletebtn.setBounds(753, 562, 191, 40);
		deletebtn.setPreferredSize(new Dimension(cancel.getIconWidth(), cancel.getIconHeight()));
		// 버튼의 크기를 이미지의 크기에 맞게 설정합니다.
		add(deletebtn);

		// 결제버튼
		ImageIcon pay = new ImageIcon("./img/menu/diycheckout-payment-button.png");
		JButton paybtn = new JButton(pay);

		paybtn.addActionListener(e -> {
			int[] insertOrderResult = orderDao.insertOrder();
			
			// OrderState의 변수 초기화
			OrderState.setUserId(0);
			OrderState.setOrderId(0);

			int tempUserId = insertOrderResult[0]; // 비회원 user_id
			int orderId = insertOrderResult[1]; // 결제 후 전화번호 입력하면 userId 업데이트 할 때 사용
			OrderState.setOrderId(orderId); // 다음 패널에 orderId를 넘겨주기 위해 static 변수 업데이트

			// orderId를 이용하여 clickCountManager에 담겨있는 <메뉴명, 수량>을 order_detail 테이블에 삽입
			System.out.println(clickCountManager);
			for (Map.Entry<String, Integer> entry : clickCountManager.entrySet()) {
				String menuName = entry.getKey();
				int quantity = entry.getValue();
				Menu menu = menudao.findByName(menuName); // 메뉴이름을 통해 menu_id 가져오기
				if (menu != null) { // order_detail 테이블에 구매한 메뉴 insert
					orderDao.insertOrderDetail(orderId, menu.getMenu_id(), quantity);
				}
			}
			// 중요: 리뷰 패널 생성 및 카드 레이아웃에 추가
			// -> reviewPanel 인스턴스 생성 시점에 orderId가 존재해야하므로 뒤로 뺐음
			ReviewPanel reviewPanel = new ReviewPanel(index);
			ContentPaneManager.getContentPane().add(reviewPanel, "review");

			// 사이드바 초기화
			resetSidebar(msbpport, Total, clickCountManager, MenuPanel.menuTotalPriceMap);
			
			MenuRankShowPane.removeAll();
			List<MenuRanking> newmenurank = menudao.getMenuRanking();
			// 각 메뉴 정보를 표시하는 컴포넌트를 반복하여 생성하여 패널에 추가 메뉴 랭킹 업데이트
			for (int i = 0; i < newmenurank.size(); i++) {
				MenuRanking menuRanking = newmenurank.get(i); // 현재 메뉴 랭킹 정보
				int rank = i + 1; // 순위
				String menuName = menuRanking.getMenuName(); // 메뉴 이름
				int orderCount = menuRanking.getTotal_order(); // 주문 횟수

				double orderPercentage = menuRanking.getTotal_percentage(); // 주문 퍼센티지
				
				// DecimalFormat 객체 생성
				DecimalFormat df = new DecimalFormat("#.#");

				// 형식화된 문자열로 변환
				String formattedAvgReview = df.format(orderPercentage);
				// 각 메뉴 정보를 표시하는 MenuShowRanks 객체 생성
				MenuShowRanks menuRankInfo = new MenuShowRanks(rank, menuName, orderCount, formattedAvgReview);
				MenuRankShowPane.add(menuRankInfo); // 패널에 추가
				MenuRankShowPane.repaint();
			}
			// 결제 완료 패널 띄우기
			CompletePaymentDialog dialog = new CompletePaymentDialog(index);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true); // 모달 다이얼로그로 설정
			dialog.setVisible(true); // 다이얼로그를 보여줌
		});

		paybtn.setPreferredSize(new Dimension(pay.getIconWidth(), pay.getIconHeight()));
		paybtn.setBounds(753, 604, 191, 40);
		add(paybtn);
		// !장바구니 & 결제 버튼

		JButton mgrbtn = new JButton("관리자");
		mgrbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 먼저 키보드 패널을 불러온다.
				CardLayoutManager.getCardLayout().show(ContentPaneManager.getContentPane(), "adminKeyboard");
				index.setBounds(0, 0, 950, 700);
				index.setLocationRelativeTo(null);
				
				// 입력한 값이 관리자 계정 정보와 맞는지 확인

				// 맞으면 아래 로직 실행
//				CardLayoutManager.getCardLayout().show(ContentPaneManager.getContentPane(), "adminPanel");

				// 틀리면 키보드 패널 새로 생성

			}
		});
		mgrbtn.setBounds(827, 6, 117, 29);
		add(mgrbtn);
		Total.setHorizontalAlignment(SwingConstants.TRAILING);
		Total.setFont(new Font("Lucida Grande", Font.PLAIN, 24));

		// 가격 총합
		Total.setForeground(new Color(0, 0, 0));
		Total.setBounds(753, 530, 191, 29);
		add(Total);

		// 메뉴 순위
		JPanel MenuRank = new JPanel();
		MenuRank.setBackground(new Color(255, 255, 255));
		MenuRank.setBounds(956, 6, 278, 638);
		add(MenuRank);
		MenuRank.setLayout(null);

		JLabel lblNewLabel = new JLabel("메뉴 순위");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Malayalam MN", Font.PLAIN, 45));
		lblNewLabel.setBounds(6, 6, 266, 88);
		MenuRank.add(lblNewLabel);

		// JPanel MenuRankShowPane = new JPanel();
		MenuRankShowPane.setBackground(new Color(255, 255, 255));
		MenuRankShowPane.setBounds(6, 135, 266, 497);
		MenuRank.add(MenuRankShowPane);

		MenuRankShowPane.setLayout(new GridLayout(menurank.size(), 1)); // 그리드 레이아웃 설정

		JLabel lblNewLabel_1 = new JLabel("총 주문 횟수(음료 제외) : ");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(6, 79, 163, 44);
		MenuRank.add(lblNewLabel_1);

		JLabel totalqalab = new JLabel("총 " + Integer.toString(totalqa) + " 회");
		totalqalab.setForeground(new Color(152, 30, 34));
		totalqalab.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		totalqalab.setHorizontalAlignment(SwingConstants.CENTER);
		totalqalab.setBounds(181, 79, 91, 44);
		MenuRank.add(totalqalab);

		// 각 메뉴 정보를 표시하는 컴포넌트를 반복하여 생성하여 패널에 추가
		for (int i = 0; i < menurank.size(); i++) {
			MenuRanking menuRanking = menurank.get(i); // 현재 메뉴 랭킹 정보
			int rank = i + 1; // 순위
			String menuName = menuRanking.getMenuName(); // 메뉴 이름
			int orderCount = menuRanking.getTotal_order(); // 주문 횟수

			double orderPercentage = menuRanking.getTotal_percentage(); // 주문 퍼센티지

			// DecimalFormat 객체 생성
			DecimalFormat df = new DecimalFormat("#.#");

			// 형식화된 문자열로 변환
			String formattedAvgReview = df.format(orderPercentage);
			// 각 메뉴 정보를 표시하는 MenuShowRanks 객체 생성
			MenuShowRanks menuRankInfo = new MenuShowRanks(rank, menuName, orderCount, formattedAvgReview);
			MenuRankShowPane.add(menuRankInfo); // 패널에 추가
		}
		// !메뉴 순위
	}

	private void loadMenuByCategory(String category) {
		System.out.println(category + " 눌림");
		List<Menu> menulist = menudao.findByCategory(category);
		menuPanels = new ArrayList<>();

		for (int i = 0; i < menulist.size(); i++) {
			menuPanel = new MenuPanel(menulist.get(i).getName(), menulist.get(i).getPrice(), msbpport,
					menulist.get(i).getMenu_id(), menulist.get(i).getMenu_path(), index);
			menuPanels.add(menuPanel);
		}
		JPanel viewport = new JPanel();
		if (menulist.size() <= 4) {
			FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 3, 0); // 오른쪽 정렬, 가로 간격 5, 세로 간격 5
			viewport.setLayout(flowLayout);
		} else {
			viewport.setLayout(new GridLayout(0, 4, 1, 1)); // 4개의 열
		}

		for (MenuPanel menuPanel : menuPanels) {
			viewport.add(menuPanel);
		}

		JScrollPane scrollPane = new JScrollPane(viewport);
		scrollPane.setPreferredSize(new Dimension(700, 500)); // 스크롤 패널의 크기 제한
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// 메뉴를 새로 로드할 때 기존에 있는 메뉴 패널을 지우고 새로운 메뉴 패널을 추가합니다.
		mainpanel.removeAll();
		mainpanel.add(scrollPane, BorderLayout.CENTER);
		mainpanel.revalidate();
		mainpanel.repaint();
	}

	public static int getClickCount(String menuName) {
		return clickCountManager.getOrDefault(menuName, 0);
	}

	// Method to update click count for a menu item
	public static void updateClickCount(String menuName, int count) {
		clickCountManager.put(menuName, count);
	}

	public static MenuSidebarPanel getMenuSidebarPanel(String menuName) {
		return sidebarPanels.get(menuName);
	}

	// Method to add a MenuSidebarPanel for a menu name
	public static void addMenuSidebarPanel(String menuName, MenuSidebarPanel panel) {
		sidebarPanels.put(menuName, panel); // Add the panel to the map
		msbpport.add(panel); // Add the panel to the container
	}

	// Method to remove a MenuSidebarPanel for a menu name
	public static void removeMenuSidebarPanel(String menuName) {
		MenuSidebarPanel sidebarPanel = sidebarPanels.remove(menuName); // 해당 메뉴 이름을 키로 사용하여 사이드바 제거
		if (sidebarPanel != null) {
			msbpport.remove(sidebarPanel); // 컨테이너에서 사이드바 제거
			msbpport.revalidate();
			msbpport.repaint();

		}
	}

	// Get the order of the sidebar panels
	public static List<String> getSidebarOrder() {
		return sidebarOrder;
	}

	public static void moveSidebarPanelToBottom(String menuName) {
		sidebarOrder.remove(menuName);
		sidebarOrder.add(menuName);
	}

	// 사이드바를 초기화하는 메서드
	public static void resetSidebar(JPanel sidebarPanel, JLabel totalLabel, Map<String, Integer> clickCountManager,
			Map<String, Integer> totalPriceMap) {
		sidebarPanel.removeAll(); // 사이드바 패널의 모든 요소 제거
		sidebarPanel.revalidate(); // 레이아웃 갱신
		sidebarPanel.repaint(); // 다시 그리기

		totalLabel.setText("0 원"); // 총액 레이블을 0원으로 설정

		// 모든 메뉴의 클릭 카운트와 총 가격을 0으로 리셋
		clickCountManager.clear();
		totalPriceMap.clear();
	}
}
