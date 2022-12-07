package Principal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class abertura extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					abertura frame = new abertura();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public abertura() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int ret = JOptionPane.showConfirmDialog(abertura.this, "Deseja encerrar o programa?");
				if (ret == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setTitle("Sistema para leitura de digitais");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 560, 180);
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

		JLabel lblNewLabel = new JLabel("Bem vindo ao sistema para leitura de digitais");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 463, 35);
		panel.add(lblNewLabel);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal ini = new principal();
				ini.setVisible(true);
				ini.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnIniciar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnIniciar.setBounds(192, 70, 160, 60);
		panel.add(btnIniciar);

		JButton btnIniciar_1_1 = new JButton("Sobre");
		btnIniciar_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sobre aux = new sobre();
				aux.setLocationRelativeTo(null);
				aux.setVisible(true);
			}
		});
		btnIniciar_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnIniciar_1_1.setBounds(10, 70, 160, 60);
		panel.add(btnIniciar_1_1);

		JButton btnIniciar_1 = new JButton("Sair");
		btnIniciar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Cancelar", "Confirmar" };
				int aux = JOptionPane.showOptionDialog(null, "Deseja realmente sair?", "Informação",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				if (aux == 1) {
					System.exit(0);
				}
			}
		});
		btnIniciar_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnIniciar_1.setBounds(374, 70, 160, 60);
		panel.add(btnIniciar_1);
	}
}