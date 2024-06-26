package com.kosa.pos.swing.main;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.kosa.pos.swing.Admin.AdminLoginKeyboardPanel;
import com.kosa.pos.swing.Admin.AdminMainPanel;
import com.kosa.pos.swing.menu.MenuDetailPanel;
import com.kosa.pos.swing.menu.MenuView;
import com.kosa.pos.swing.menu.menutest;
import com.kosa.pos.swing.signUp.KeyboardPanel;

public class Index extends JFrame {
	// 싱글톤으로 지정
	private JPanel contentPane;
	private CardLayout cardLayout;

	public Index() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 1250, 700); //1250 or 950
		setLocationRelativeTo(null);
		cardLayout = CardLayoutManager.getCardLayout();
		// contentPane = new JPanel(cardLayout);
		contentPane = ContentPaneManager.getContentPane();

		add(contentPane);
		MenuView menuPanel = new MenuView(this);
		
		MenuDetailPanel mdp = new MenuDetailPanel();
		mdp.setName("mdp");
		
		KeyboardPanel keyboardPanel = new KeyboardPanel(this);
		AdminLoginKeyboardPanel adminKeyboardPanel = new AdminLoginKeyboardPanel(this);

		AdminMainPanel adminPanel = new AdminMainPanel(this);
		adminPanel.setName("adminPanel");

		menutest cp = new menutest();

		contentPane.add(menuPanel, "menu");
		contentPane.add(mdp, "menudetail");
		contentPane.add(cp, "cp");
		contentPane.add(keyboardPanel, "keyboard");
		contentPane.add(adminKeyboardPanel, "adminKeyboard");
		contentPane.add(adminPanel, "adminPanel");
		cardLayout.show(contentPane, "menu");
	}
	
}
