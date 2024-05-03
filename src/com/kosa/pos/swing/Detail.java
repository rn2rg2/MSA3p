package com.kosa.pos.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Detail {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail window = new Detail();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Detail() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 984, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 970, 666);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel menuImage = new JPanel();
		menuImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuImage.setBounds(59, 46, 409, 321);
		
		
		// 이미지 넣기
//		ImageIcon icon = new ImageIcon("");
//		JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
//		menuImage.add(iconLabel);
		
		panel.add(menuImage);
		menuImage.setLayout(null);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		ImageIcon icon = new ImageIcon("C:\\\\Users\\\\KOSA\\\\Desktop\\\\이종섭\\\\프로젝트 - 1차\\\\햄버거 사진.jpg");
		
		lblNewLabel_6.setIcon(icon);
		lblNewLabel_6.setBounds(0, 0, 409, 321);
		menuImage.add(lblNewLabel_6);
		
		JPanel menuExplain = new JPanel();
		menuExplain.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuExplain.setBounds(59, 391, 409, 161);
		panel.add(menuExplain);
		menuExplain.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 0, 409, 161);
		menuExplain.add(scrollPane_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아ㅍ메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명메뉴설명ㅍ");
		textPane.setEditable(false);
		scrollPane_1.setViewportView(textPane);
		
		JPanel menuPrice = new JPanel();
		menuPrice.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuPrice.setBounds(493, 46, 441, 73);
		panel.add(menuPrice);
		menuPrice.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("10,000원");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 10, 417, 53);
		menuPrice.add(lblNewLabel);
		
		JPanel menuStats = new JPanel();
		menuStats.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuStats.setBounds(493, 140, 441, 119);
		panel.add(menuStats);
		menuStats.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("최근(일주일)");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(23, 22, 130, 35);
		menuStats.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("128");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(lblNewLabel_2.getFont().deriveFont(lblNewLabel_2.getFont().getStyle() | Font.BOLD, 20f));
		lblNewLabel_2.setBounds(152, 22, 62, 35);
		menuStats.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("번 주문받은 메뉴입니다.");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(195, 22, 234, 35);
		menuStats.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("리뷰 평균");
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(129, 67, 99, 42);
		menuStats.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("4.5");
		lblNewLabel_5.setFont(lblNewLabel_5.getFont().deriveFont(lblNewLabel_5.getFont().getStyle() | Font.BOLD, 20f));
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(240, 67, 39, 38);
		menuStats.add(lblNewLabel_5);
		
		JPanel menuReviewTable = new JPanel();
		menuReviewTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		menuReviewTable.setBounds(493, 269, 441, 283);
		panel.add(menuReviewTable);
		menuReviewTable.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("최신순");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_1.setBounds(0, 0, 143, 57);
		menuReviewTable.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("평점순");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_2.setBounds(142, 0, 157, 57);
		menuReviewTable.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("오래된순");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_3.setBounds(298, 0, 143, 57);
		menuReviewTable.add(btnNewButton_3);
		
        // JPanel 생성
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

        // JScrollPane에 넣을 레이블 추가
        for (int i = 1; i <= 10; i++) {
        	JPanel cellPanel_1 = new JPanel();
        	cellPanel_1.setLayout(new BorderLayout());
        	
        	// 번호
            JLabel phoneNumberLabel = new JLabel("010-1234-****");
            phoneNumberLabel.setHorizontalAlignment(JLabel.CENTER);
            phoneNumberLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            cellPanel_1.add(phoneNumberLabel, BorderLayout.WEST);
            
            // 날짜
            JLabel dayLabel = new JLabel("2024-05-03 10:45:12");
            dayLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            cellPanel_1.add(dayLabel, BorderLayout.EAST);
            
        	JPanel cellPanel_2 = new JPanel();
        	cellPanel_2.setLayout(new BorderLayout());
        	cellPanel_2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        	
        	// 리뷰
            JLabel review = new JLabel("정말 맛있어요~~!!!");
            review.setHorizontalAlignment(JLabel.CENTER);
            review.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            cellPanel_2.add(review, BorderLayout.WEST);
            
            // 평점
            JLabel reviewScore = new JLabel("4.5"+"점");
            reviewScore.setBorder(BorderFactory.createEmptyBorder(10,10,10,40));
            cellPanel_2.add(reviewScore, BorderLayout.EAST);
            
            panel_1.add(cellPanel_1);
            panel_1.add(cellPanel_2);
            
        }
		
		
		JScrollPane scrollPane = new JScrollPane(panel_1);
		scrollPane.setBounds(0, 55, 441, 228);
		menuReviewTable.add(scrollPane);
		
		JButton btnNewButton = new JButton("뒤로 가기");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(644, 573, 290, 65);
		panel.add(btnNewButton);
	}
}