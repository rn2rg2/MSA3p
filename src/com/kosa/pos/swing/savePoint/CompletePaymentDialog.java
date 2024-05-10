package com.kosa.pos.swing.savePoint;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.kosa.pos.swing.main.CardLayoutManager;
import com.kosa.pos.swing.main.ContentPaneManager;

public class CompletePaymentDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public CompletePaymentDialog() {
		initUI();
	}

	private void initUI() {
		getContentPane().setLayout(null); // 절대 위치 지정
		setSize(627, 458);

		JLabel lblCompletePayment = new JLabel("결제 완료!");
		lblCompletePayment.setFont(new Font("굴림", Font.PLAIN, 20));
		lblCompletePayment.setBounds(262, 139, 103, 54);
		getContentPane().add(lblCompletePayment);

		JLabel lblDoYouSave = new JLabel("적립하시겠습니까?");
		lblDoYouSave.setFont(new Font("굴림", Font.PLAIN, 20));
		lblDoYouSave.setBounds(225, 196, 176, 63);
		getContentPane().add(lblDoYouSave);

		JButton yesButton = new JButton("네");
		yesButton.setFont(new Font("굴림", Font.PLAIN, 17));
		yesButton.setBounds(118, 269, 147, 54);
		getContentPane().add(yesButton);

		JButton noButton = new JButton("아니요");
		noButton.setFont(new Font("굴림", Font.PLAIN, 17));
		noButton.setBounds(362, 269, 147, 54);
		getContentPane().add(noButton);

		/* 리스너 설정 */
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CardLayoutManager.getCardLayout().show(ContentPaneManager.getContentPane(), "keyboard");
			}
		});

		// TODO
		// 1. "아니요" 버튼 눌렀을 때 동작 -> 메인 화면으로 돌아가기
	}
}
