package Principal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class sobre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public sobre() {
		setTitle("Sistema para leitura de digitais");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		panel.setBorder(null);
		contentPane.add(panel);

		JButton btnNewButton = new JButton("Sair");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(382, 260, 142, 40);
		panel.add(btnNewButton);

		JTextPane txtpnEstaAplicaoFoi = new JTextPane();
		txtpnEstaAplicaoFoi.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnEstaAplicaoFoi.setText(
				"Esta aplicação foi desenvolvida para gerenciamento de níveis de acesso entre determinados usuários para um cenário onde haveria restrições de entrada ao banco de dados Ministério do Meio Ambiente, onde é feito o reconhecimento dos padrões de suas digitais e verificado em comunicação com um banco de dados.\r\n\r\nIntegrantes do grupo:\r\nN560JC5 - GABRIEL LIMA DE ALMEIDA\r\nN652985 - GUSTAVO BOTECHIA SCHREIBER \r\nF316GB1 - MARCELO CAETANO DE LIMA \r\nN545FH8 - MATHEUS TOMKIO DE FREITAS \r\nF037711 - PEDRO HENRIQUE A DE OLIVEIRA");
		txtpnEstaAplicaoFoi.setBounds(10, 11, 514, 237);
		panel.add(txtpnEstaAplicaoFoi);
	}
}