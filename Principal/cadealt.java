package Principal;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import Biometrics.CFingerPrint;
import util.conexao;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class cadealt extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nome;
	private JButton btnNewButton;
	private JTextField user;
	private JPasswordField password;
	public String digitalLida;
	public String caminho;
	private CFingerPrint m_finger1 = new CFingerPrint();
	private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
	private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private String useradm = "admin";
	private String passadm = "admin";
	private JLabel lblNewLabel;
	private JLabel lblInformeONome;
	private JLabel lblInformeONvel;
	private JLabel lblInformeOUsurio;
	private JLabel lblInformeASenha;

	public cadealt() {
		setTitle("Sistema para leitura de digitais");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 670, 310);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);
		contentPane.add(panel);

		nome = new JTextField();
		nome.setColumns(10);
		nome.setBounds(214, 47, 430, 20);
		panel.add(nome);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(214, 78, 430, 22);
		comboBox.addItem("---");
		comboBox.addItem("1");
		comboBox.addItem("2");
		comboBox.addItem("3");
		panel.add(comboBox);

		btnNewButton = new JButton("Selecionar digital");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser sel = new JFileChooser("C:\\DG");
				sel.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivo de imagem .tif", "tif");
				sel.setFileFilter(filtro);
				sel.setDialogTitle("Selecione a digital");
				sel.setVisible(true);
				File file = new File("");

				int resposta = sel.showOpenDialog(new JDialog());

				if (resposta == JFileChooser.APPROVE_OPTION) {
					file = sel.getSelectedFile();
					caminho = file.getAbsolutePath();
					try {
						m_bimage1 = ImageIO.read(new File(file.getAbsolutePath()));
						m_finger1.setFingerPrintImage(m_bimage1);
						finger1 = m_finger1.getFingerPrintTemplate();
						m_bimage1 = m_finger1.getFingerPrintImageDetail();
						digitalLida = m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1);
						System.out.println(digitalLida);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setBounds(10, 109, 634, 23);
		panel.add(btnNewButton);

		user = new JTextField();
		user.setColumns(10);
		user.setBounds(214, 143, 430, 20);
		panel.add(user);

		password = new JPasswordField();
		password.setBounds(214, 174, 430, 20);
		panel.add(password);

		JButton btnExcluirDigital = new JButton("Excluir digital");
		btnExcluirDigital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao dao = new conexao();

				if (nome.getText().equals("") || !user.getText().equals(useradm)
						|| !String.valueOf(password.getPassword()).equals(passadm)) {
					JOptionPane.showMessageDialog(null, "Dados incorretos / faltantes");
				} else {
					try {
						dao.removeOfDataBase(nome.getText());
						JOptionPane.showMessageDialog(null, "Usuário removido");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnExcluirDigital.setForeground(Color.WHITE);
		btnExcluirDigital.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExcluirDigital.setBackground(Color.DARK_GRAY);
		btnExcluirDigital.setBounds(10, 205, 316, 55);
		panel.add(btnExcluirDigital);

		JButton btnNewButton_1_1 = new JButton("Cadastar digital");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao dao = new conexao();

				if (digitalLida == null || nome.getText() == null || comboBox.getSelectedItem() == "---"
						|| !user.getText().equals(useradm) || !String.valueOf(password.getPassword()).equals(passadm)) {
					JOptionPane.showMessageDialog(null, "Dados incorretos / faltantes");
				} else {
					try {
						dao.writeDataBase(nome.getText(), Integer.parseInt((String) comboBox.getSelectedItem()),
								digitalLida);
						JOptionPane.showMessageDialog(null, "Usuário registrado");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1_1.setBackground(Color.DARK_GRAY);
		btnNewButton_1_1.setBounds(336, 205, 308, 55);
		panel.add(btnNewButton_1_1);

		lblNewLabel = new JLabel("Cadastro / Exclusão de digital");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.DARK_GRAY);
		lblNewLabel.setBounds(10, 11, 634, 20);
		panel.add(lblNewLabel);

		lblInformeONome = new JLabel("Informe o nome da pessoa:");
		lblInformeONome.setOpaque(true);
		lblInformeONome.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformeONome.setForeground(Color.WHITE);
		lblInformeONome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInformeONome.setBackground(Color.DARK_GRAY);
		lblInformeONome.setBounds(10, 47, 194, 20);
		panel.add(lblInformeONome);

		lblInformeONvel = new JLabel("Informe o nível de acesso:");
		lblInformeONvel.setOpaque(true);
		lblInformeONvel.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformeONvel.setForeground(Color.WHITE);
		lblInformeONvel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInformeONvel.setBackground(Color.DARK_GRAY);
		lblInformeONvel.setBounds(10, 78, 194, 20);
		panel.add(lblInformeONvel);

		lblInformeOUsurio = new JLabel("Informe o usuário administrador:");
		lblInformeOUsurio.setOpaque(true);
		lblInformeOUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformeOUsurio.setForeground(Color.WHITE);
		lblInformeOUsurio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInformeOUsurio.setBackground(Color.DARK_GRAY);
		lblInformeOUsurio.setBounds(10, 143, 194, 20);
		panel.add(lblInformeOUsurio);

		lblInformeASenha = new JLabel("Informe a senha administrador:");
		lblInformeASenha.setOpaque(true);
		lblInformeASenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformeASenha.setForeground(Color.WHITE);
		lblInformeASenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInformeASenha.setBackground(Color.DARK_GRAY);
		lblInformeASenha.setBounds(10, 174, 194, 20);
		panel.add(lblInformeASenha);
	}
}