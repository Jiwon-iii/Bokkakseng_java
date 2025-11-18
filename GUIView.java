package zzz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import project.MemberDAOImpl;

import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import java.awt.Component;
import javax.swing.JPasswordField;
import javax.swing.JLayeredPane;

public class GUIView extends JFrame {
	
	MemberDAOImpl mdao=new MemberDAOImpl();
	
	private JPanel contentPane;
	private JSplitPane splitPane;
	private JPanel pn_study;
	static private JTabbedPane tabbedPane; 
	private JPanel tabList;
	private JPanel tabTest;
	private JPanel panel_Edit;
	private JPanel pn_login;
	private JLabel lbl_ID;
	private JLabel lbl_ID_1;
	private JTextField tfID;
	private JButton btnLogin;
	private JButton btnSignup;
	private JPasswordField tfPW;

    public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GUIView frame = new GUIView();
						frame.setVisible(true);
						tabbedPane.setEnabledAt(1, false); //문제풀기 탭 비활성화 (로그인 시 가능)
						tabbedPane.setEnabledAt(2, false); //단어추가 탭 비활성화
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		/**
		 * Create the frame.
		 */
		public GUIView() {
			setTitle("영단어 암기 프로그램");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 570);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			contentPane.add(getSplitPane());
		}
        private JSplitPane getSplitPane() {
			if (splitPane == null) {
				splitPane = new JSplitPane();
				splitPane.setBounds(5, 5, 418, 504);
				splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
				splitPane.setRightComponent(getPn_study());
				splitPane.setLeftComponent(getPn_login());
				splitPane.setDividerLocation(80);
			}
			return splitPane;
		}
        private JPanel getPn_study() {
			if (pn_study == null) {
				pn_study = new JPanel();
				pn_study.setLayout(new BorderLayout(0, 0));
				pn_study.add(getTabbedPane(), BorderLayout.CENTER);
			}
			return pn_study;
		}
        private JTabbedPane getTabbedPane() {
			if (tabbedPane == null) {
				tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			}
			return tabbedPane;
		}
        private JPanel getPn_login() {
			if (pn_login == null) {
				pn_login = new JPanel();
				pn_login.setLayout(null);
				pn_login.add(getLbl_ID());
				pn_login.add(getLbl_ID_1());
				pn_login.add(getTfID());
				pn_login.add(getBtnLogin());
				pn_login.add(getBtnSignup());
				pn_login.add(getTfPW());
				pn_login.add(getBtnLogout());
				pn_login.add(getBtnManager());
			}
			return pn_login;
		}
		private JLabel getLbl_ID() {
			if (lbl_ID == null) {
				lbl_ID = new JLabel("아이디");
				lbl_ID.setFont(new Font("돋움", Font.BOLD, 14));
				lbl_ID.setBounds(8, 12, 79, 23);
				lbl_ID.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			return lbl_ID;
		}
		private JLabel getLbl_ID_1() {
			if (lbl_ID_1 == null) {
				lbl_ID_1 = new JLabel("패스워드");
				lbl_ID_1.setHorizontalAlignment(SwingConstants.RIGHT);
				lbl_ID_1.setFont(new Font("돋움", Font.BOLD, 14));
				lbl_ID_1.setBounds(8, 45, 79, 23);
			}
			return lbl_ID_1;
		}
		private JButton btnLogout;
		private JButton btnManager;
//		<로그인 패널>-아이디 텍스트필드 tfID 
			private JTextField getTfID() {
				if (tfID == null) {
					tfID = new JTextField();
					tfID.setBounds(103, 15, 112, 20);
					tfID.setColumns(10);
				}
				return tfID;
			}
		//	<로그인 패널>-패스워드필드 tfPW 
			private JPasswordField getTfPW() {
				if (tfPW == null) {
					tfPW = new JPasswordField();
					tfPW.setBounds(103, 47, 112, 22);
				}
				return tfPW;
			}
        //	<로그인 패널>-로그인버튼 btnLogin-아이디 패스워드 일치여부 확인(경고창)+어떻게 로그인을 할것인가? 어떻게 유저별로 성적을 저장하게 할 것인가?
		private JButton getBtnLogin() {
			if (btnLogin == null) {
				btnLogin = new JButton("Login");
				btnLogin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String id=tfID.getText().trim();//아이디 저장
						//패스워드 저장
						char[]secret_pw=tfPW.getPassword(); 
						String pw="";
						for(char cha:secret_pw) {
							Character.toString(cha);
							pw+=cha; 
						}
						int result=3;
						result=mdao.loginCheck(id,pw);
						if(result==0) { //일반 회원 로그인
							JOptionPane.showMessageDialog(null, "로그인성공");
							btnLogin.setVisible(false); //로그인버튼 비활성화
							btnSignup.setVisible(true); //가입버튼 활성화
							btnLogout.setVisible(true); //로그아웃버튼 활성화
							btnManager.setVisible(false); //관리버튼 비활성화
							tfID.setEnabled(false);
							tfPW.setEnabled(false);
							tabbedPane.setEnabledAt(1, true);
						}else {
							JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호 오류");
						}
						//관리자 로그인
						if(id.equals("admin")&&pw.equals("admin")) {
							JOptionPane.showMessageDialog(null, "관리자로 로그인 되었습니다.");
							btnLogin.setVisible(false); //로그인버튼 비활성화
							btnSignup.setVisible(false); //가입버튼 비활성화
							btnLogout.setVisible(true); //로그아웃버튼 활성화
							btnManager.setVisible(true); //관리버튼 활성화
							tabbedPane.setEnabledAt(2, true); //단어추가 탭 활성화
						}
					}
				});
				btnLogin.setBounds(230, 14, 82, 54);
			}
			return btnLogin;
		}
	//	<로그인 패널>-가입버튼 btnSignup
		private JButton getBtnSignup() {
			if (btnSignup == null) {
				btnSignup = new JButton("가입");
				btnSignup.setActionCommand("가입");
				btnSignup.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SignupView frame = new SignupView(); //객체 생성. 버튼과 연결하면 창이 뜸.
						frame.setVisible(true);
	//					dispose(); //다른 창을 띄었을때 뒤에 있는 창을 닫음
					}
				});
				btnSignup.setBounds(328, 14, 82, 54);
			}
			return btnSignup;
		}
	//	<로그인 패널>-로그아웃버튼 btnLogout
		private JButton getBtnLogout() {
			if (btnLogout == null) {
				btnLogout = new JButton("Logout");
				btnLogout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnLogin.setVisible(true);
						btnLogout.setVisible(false);
						btnSignup.setVisible(true);
						btnManager.setVisible(false);
						tfID.setEnabled(true);
						tfPW.setEnabled(true);
						tfID.setText("");
						tfPW.setText("");
						tabbedPane.setSelectedIndex(0); //단어목록 탭으로 이동
						tabbedPane.setEnabledAt(1, false); //문제풀기 탭 비활성화
						tabbedPane.setEnabledAt(2, false); //단어추가 탭 비활성화
					}
				});
				btnLogout.setBounds(230, 14, 82, 54);
				btnLogout.setVisible(false);
			}
			return btnLogout;
		}
	//	<로그인 패널>-관리버튼 btnManager
		private JButton getBtnManager() {
			if (btnManager == null) {
				btnManager = new JButton("관리");
				btnManager.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AdminView frame = new AdminView(); //AdminView 프레임 창이 뜬다.
						frame.setVisible(true);
					}
				});
				btnManager.setBounds(328, 14, 82, 54);
				btnManager.setVisible(false);
			}
			return btnManager;
		}
}