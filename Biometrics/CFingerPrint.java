package Biometrics;

import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;

public class CFingerPrint {
	public int FP_IMAGE_WIDTH = 323;
	public int FP_IMAGE_HEIGHT = 352;
	public final int FP_TEMPLATE_MAX_SIZE = 601;
	public int FP_MATCH_POINT_DISTANCE_MOVEMENT = 10;
	public int FP_MATCH_POINT_ROTATION_MOVEMENT = 10;// 10;
	public int FP_MATCH_THRESHOLD = 55;
	final public int FP_CLASS_WHORL = 1;
	final public int FP_CLASS_LEFT_LOOP = 2;
	final public int FP_CLASS_RIGHT_LOOP = 3;
	final public int FP_CLASS_ARCH = 4;
	final public int FP_CLASS_ARCH_TENTED = 5;
	final private int FP_TEMPLATE_SEARCH_RADIUS = 1;
	public byte P[][] = new byte[FP_IMAGE_WIDTH][FP_IMAGE_HEIGHT];

	public CFingerPrint() {
	}

	public CFingerPrint(int width, int height) {
		FP_IMAGE_WIDTH = width;
		FP_IMAGE_HEIGHT = height;
		P = new byte[width][height];
	}

	public CFingerPrint(int width, int height, int MatchPointDistanceMovement, int MatchPointRotationMovment,
			int MatchThreshold) {
		FP_IMAGE_WIDTH = width;
		FP_IMAGE_HEIGHT = height;
		FP_MATCH_POINT_DISTANCE_MOVEMENT = MatchPointDistanceMovement;
		FP_MATCH_POINT_ROTATION_MOVEMENT = MatchPointRotationMovment;
		FP_MATCH_THRESHOLD = MatchThreshold;
	}

	public void setFingerPrintImage(BufferedImage m_image) {
		for (int i = 0; i <= FP_IMAGE_WIDTH - 1; i++) {
			for (int j = 0; j <= FP_IMAGE_HEIGHT - 1; j++) {
				Color c = new Color(m_image.getRGB(i, j));
				if ((c.getBlue() <= 127) && (c.getRed() <= 127) && (c.getGreen() <= 127)) {
					P[i][j] = 1;
				} else {
					P[i][j] = 0;
				}
			}
		}
		for (int i = 0; i <= FP_IMAGE_WIDTH - 1; i++) {
			P[i][0] = 0;
			P[i][FP_IMAGE_HEIGHT - 1] = 0;
		}
		for (int j = 0; j <= FP_IMAGE_HEIGHT - 1; j++) {
			P[0][j] = 0;
			P[FP_IMAGE_WIDTH - 1][j] = 0;
		}
	}

	public BufferedImage getFingerPrintImage() {
		BufferedImage m_ImageBuffer = new BufferedImage(FP_IMAGE_WIDTH, FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i <= FP_IMAGE_WIDTH - 1; i++) {
			for (int j = 0; j <= FP_IMAGE_HEIGHT - 1; j++) {
				if (P[i][j] == 1)
					m_ImageBuffer.setRGB(i, j, 0);
				else
					m_ImageBuffer.setRGB(i, j, Color.white.getRGB());
			}
		}
		return m_ImageBuffer;
	}

	public BufferedImage getFingerPrintImageDetail() {
		BufferedImage m_ImageBuffer = new BufferedImage(FP_IMAGE_WIDTH, FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i <= FP_IMAGE_WIDTH - 1; i++) {
			for (int j = 0; j <= FP_IMAGE_HEIGHT - 1; j++) {
				if (P[i][j] == 1)
					m_ImageBuffer.setRGB(i, j, Color.blue.getRGB());
				else
					m_ImageBuffer.setRGB(i, j, Color.white.getRGB());
			}
		}

		double m_arr[] = this.getFingerPrintTemplate();

		Graphics2D gf = m_ImageBuffer.createGraphics();
		gf.setColor(Color.red);
		for (int i = 7; i <= m_arr[0] - 1; i = i + 6) {
			if (m_arr[i + 4] > 1) {
				gf.setColor(Color.red);
				gf.drawRect((int) m_arr[i] + (int) m_arr[1] - 3, (int) m_arr[i + 1] + (int) m_arr[2] - 2, 5, 5);
			} else if (m_arr[i + 4] == 1) {
				gf.setColor(Color.GREEN);
				gf.drawRect((int) m_arr[i] + (int) m_arr[1] - 3, (int) m_arr[i + 1] + (int) m_arr[2] - 2, 5, 5);
			}

		}
		gf.setColor(Color.gray);

		gf.drawLine((int) m_arr[1] - 5, (int) m_arr[2], (int) m_arr[1] + 5, (int) m_arr[2]);
		gf.drawLine((int) m_arr[1], (int) m_arr[2] - 5, (int) m_arr[1], (int) m_arr[2] + 5);
		gf.drawImage(m_ImageBuffer, null, FP_IMAGE_WIDTH, FP_IMAGE_WIDTH);
		return m_ImageBuffer;
	}

	public void ThinningHilditch() {
		int change = 1;
		boolean mbool = true;
		while (change != 0) {
			change = 0;
			for (int i = 2; i <= FP_IMAGE_WIDTH - 2; i++) {
				for (int j = 2; j <= FP_IMAGE_HEIGHT - 2; j++) {
					if (P[i][j] == 1) {
						short c = 0;
						if (P[i][j + 1] == 1) {
							c++;
						}
						if (P[i + 1][j + 1] == 1) {
							c++;
						}
						if (P[i + 1][j] == 1) {
							c++;
						}
						if (P[i + 1][j - 1] == 1) {
							c++;
						}
						if (P[i][j - 1] == 1) {
							c++;
						}
						if (P[i - 1][j - 1] == 1) {
							c++;
						}
						if (P[i - 1][j] == 1) {
							c++;
						}
						if (P[i - 1][j + 1] == 1) {
							c++;
						}

						if ((c >= 2) && (c <= 6)) {
							c = 0;
							if ((P[i - 1][j + 1] == 0) && (P[i][j + 1] == 1)) {
								c++;
							}
							if ((P[i][j + 1] == 0) && (P[i + 1][j + 1] == 1)) {
								c++;
							}
							if ((P[i + 1][j + 1] == 0) && (P[i + 1][j] == 1)) {
								c++;
							}
							if ((P[i + 1][j] == 0) && (P[i + 1][j - 1] == 1)) {
								c++;
							}
							if ((P[i + 1][j - 1] == 0) && (P[i][j - 1] == 1)) {
								c++;
							}
							if ((P[i][j - 1] == 0) && (P[i - 1][j - 1] == 1)) {
								c++;
							}
							if ((P[i - 1][j - 1] == 0) && (P[i - 1][j] == 1)) {
								c++;
							}
							if ((P[i - 1][j] == 0) && (P[i - 1][j + 1] == 1)) {
								c++;
							}

							if (c == 1) {
								c = 0;
								if (mbool == true) {
									if ((P[i][j + 1] * P[i + 1][j] * P[i + 1][j - 1]) == 0) {
										if ((P[i + 1][j] * P[i + 1][j - 1] * P[i - 1][j]) == 0) {
											P[i][j] = 0;
											change++;
										}
									}
									mbool = false;
								} else {
									if ((P[i][j + 1] * P[i + 1][j - 1] * P[i - 1][j]) == 0) {
										if ((P[i][j + 1] * P[i + 1][j] * P[i - 1][j]) == 0) {
											P[i][j] = 0;
											change++;
										}
									}
									mbool = true;
								}
							}
						}
					}
				}
			}
		}
	}

	public void ThinningHitAndMiss() {
		int c = 1;
		while (c != 0) {
			c = 0;
			for (int i = 1; i <= FP_IMAGE_WIDTH - 1; i++) {
				for (int j = 1; j <= FP_IMAGE_HEIGHT - 1; j++) {
					if ((P[i][j] == 1) && (i != 0) && (j != FP_IMAGE_HEIGHT - 1) && (j != 0)
							&& (i != FP_IMAGE_WIDTH - 1)) {
						if ((P[i - 1][j - 1] == 1) && (P[i][j - 1] == 1) && (P[i + 1][j - 1] == 1)
								&& (P[i - 1][j + 1] == 0) && (P[i][j + 1] == 0) && (P[i + 1][j + 1] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i - 1][j + 1] == 1) && (P[i][j + 1] == 1) && (P[i + 1][j + 1] == 1)
								&& (P[i - 1][j - 1] == 0) && (P[i][j - 1] == 0) && (P[i + 1][j - 1] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i - 1][j] == 1) && (P[i - 1][j - 1] == 1) && (P[i - 1][j + 1] == 1)
								&& (P[i + 1][j] == 0) && (P[i + 1][j + 1] == 0) && (P[i + 1][j - 1] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i + 1][j] == 1) && (P[i + 1][j - 1] == 1) && (P[i + 1][j + 1] == 1)
								&& (P[i - 1][j] == 0) && (P[i - 1][j + 1] == 0) && (P[i - 1][j - 1] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i - 1][j] == 1) && (P[i][j - 1] == 1) && (P[i][j + 1] == 0)
								&& (P[i + 1][j + 1] == 0) && (P[i + 1][j] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i - 1][j] == 1) && (P[i][j + 1] == 1) && (P[i][j - 1] == 0)
								&& (P[i + 1][j - 1] == 0) && (P[i + 1][j] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i][j + 1] == 1) && (P[i + 1][j] == 1) && (P[i - 1][j] == 0)
								&& (P[i - 1][j - 1] == 0) && (P[i][j - 1] == 0)) {
							P[i][j] = 0;
							c++;
						} else if ((P[i][j - 1] == 1) && (P[i + 1][j] == 1) && (P[i - 1][j] == 0)
								&& (P[i - 1][j + 1] == 0) && (P[i][j + 1] == 0)) {
							P[i][j] = 0;
							c++;
						}
					}
				}
			}
		}
	}

	public void ChaneLinkAlgorithm(int ChainLinkDistance) {
		for (int i = 1; i <= FP_IMAGE_WIDTH - 1; i++) {
			for (int j = 1; j <= FP_IMAGE_HEIGHT - 1; j++) {
				if ((P[i][j] == 1) && (i != FP_IMAGE_WIDTH - 1) && (i != 0) && (j != FP_IMAGE_HEIGHT - 1) && (j != 0)) {
					if (P[i + 1][j] == 0) {
						short countX = 0;
						while (((i + countX) <= FP_IMAGE_WIDTH - 1) && (countX <= ChainLinkDistance)) {
							if (((i + countX + 1) <= FP_IMAGE_WIDTH - 1) && ((countX + 1) <= ChainLinkDistance)) {
								if (P[i + countX + 1][j] == 0) {
									countX++;
								} else {
									break;
								}
							} else {
								break;
							}

						}
						if ((countX != 0) && ((countX + 1) <= ChainLinkDistance)) {
							for (int temp = 0; temp <= countX; temp++) {
								P[i + temp][j] = 1;
							}
						}
					}
				}

				if ((P[i][j] == 1) && (i != FP_IMAGE_WIDTH - 1) && (i != 0) && (j != FP_IMAGE_HEIGHT - 1) && (j != 0)) {
					if (P[i][j + 1] == 0) {
						short countY = 0;

						while (((j + countY) <= FP_IMAGE_HEIGHT - 1) && (countY <= ChainLinkDistance)) {
							if (((j + countY + 1) <= FP_IMAGE_HEIGHT - 1) && ((countY + 1) <= ChainLinkDistance)) {
								if (P[i][j + countY + 1] == 0) {
									countY++;
								} else {
									break;
								}
							} else {
								break;
							}

						}
						if ((countY != 0) && (countY + 1 <= ChainLinkDistance)) {
							for (int temp = 0; temp <= countY; temp++) {
								P[i][j + temp] = 1;
							}
						}
					}
				}

				if ((P[i][j] == 1) && (i != FP_IMAGE_WIDTH - 1) && (i != 0) && (j != FP_IMAGE_HEIGHT - 1) && (j != 0)) {
					if (P[i + 1][j + 1] == 0) {
						short countYX = 0;

						while ((j + countYX <= FP_IMAGE_HEIGHT - 1) && (i + countYX <= FP_IMAGE_WIDTH - 1)
								&& (countYX <= ChainLinkDistance)) {
							if (((j + countYX + 1) <= FP_IMAGE_HEIGHT - 1) && ((i + countYX + 1) <= FP_IMAGE_WIDTH - 1)
									&& ((countYX + 1) <= ChainLinkDistance))
								if (P[i + countYX + 1][j + countYX + 1] == 0) {
									countYX++;
								} else {
									break;
								}

							else {
								break;
							}
						}
						if ((countYX != 0) && (countYX + 1 <= ChainLinkDistance)) {
							for (int temp = 0; temp <= countYX; temp++) {
								P[i + temp][j + temp] = 1;
							}
						}
					}
				}
			}
		}
	}

	private Point getFingerPrintOrigin() {
		Point m_Point = new Point();
		double gradcur = 0;
		double gradprev = 0;
		double gradchangebig = 0;
		double gradchange = 0;

		double graddistancebig = 0;
		double graddistance = 0;

		double prevx = 0;

		for (int j = 50; j <= FP_IMAGE_HEIGHT - 50; j++) {
			for (int i = 50; i <= FP_IMAGE_WIDTH - 50; i++) {
				if (P[i][j] == 1) {
					int tc = 0;
					int x1 = 0;
					int y1 = 0;
					int x2 = 0;
					int y2 = 0;
					for (int m = -1 * FP_TEMPLATE_SEARCH_RADIUS; m <= FP_TEMPLATE_SEARCH_RADIUS; m++) {
						for (int n = -1 * FP_TEMPLATE_SEARCH_RADIUS; n <= FP_TEMPLATE_SEARCH_RADIUS; n++) {
							if ((m == FP_TEMPLATE_SEARCH_RADIUS) || (m == (-1) * FP_TEMPLATE_SEARCH_RADIUS)
									|| (n == FP_TEMPLATE_SEARCH_RADIUS) || (n == (-1) * FP_TEMPLATE_SEARCH_RADIUS)) {
								if (P[i + m][j + n] == 1) {
									tc++;
									if (tc == 1) {
										x1 = i + m;
										y1 = j + n;
									}
									if (tc == 2) {
										x2 = i + m;
										y2 = j + n;
									}
								}
							}
						}
					}
					if (tc == 2) {
						if ((x2 - x1) > 0) {
							gradcur = (y2 - y1) / (x2 - x1);

							if ((gradcur > 0) && (gradprev < 0)) {
								gradchange = Math.abs(gradcur) + Math.abs(gradprev);
								graddistance = Math.abs(i) - Math.abs(prevx);
								if (gradchangebig < gradchange) {
									if (graddistancebig < graddistance) {
										gradchangebig = gradchange;
										graddistancebig = graddistance;
										m_Point.x = i;
										m_Point.y = j;
									}
								}
								break;
							}
							gradprev = gradcur;
							gradcur = 0;
							prevx = i;
						}
					}
				}
			}
		}

		return m_Point;
	}

	public double[] getFingerPrintTemplate() {
		double x = 0;
		double y = 0;
		double r = 0;
		double d = 0;
		double m_arr[] = new double[FP_TEMPLATE_MAX_SIZE];

		this.ThinningHilditch();
		this.ThinningHitAndMiss();
		this.ThinningHilditch();
		this.ThinningHitAndMiss();

		Point origin = this.getFingerPrintOrigin();
		m_arr[1] = origin.x;
		m_arr[2] = origin.y;

		int c = 7;
		int previ = 0;
		int prevj = 0;

		boolean first = true;

		for (int j = 5; j <= FP_IMAGE_HEIGHT - 6; j++) {
			first = true;
			for (int i = 5; i <= FP_IMAGE_WIDTH - 6; i++) {
				if ((c < FP_TEMPLATE_MAX_SIZE) && (P[i][j] == 1) && (i != FP_IMAGE_WIDTH - 1) && (i != 0)
						&& (j != FP_IMAGE_HEIGHT - 1) && (j != 0)) {
					if (first == true) {
						first = false;
						if ((c > 7) && ((m_arr[c - 6] + origin.x) == previ) && ((m_arr[c - 5] + origin.y) == prevj)) {
							m_arr[c--] = 0;
							m_arr[c--] = 0;
							m_arr[c--] = 0;
							m_arr[c--] = 0;
							m_arr[c--] = 0;
							m_arr[c--] = 0;
						}
					} else {
						int tc = 0;
						for (int m = -1 * FP_TEMPLATE_SEARCH_RADIUS; m <= FP_TEMPLATE_SEARCH_RADIUS; m++) {
							for (int n = -1 * FP_TEMPLATE_SEARCH_RADIUS; n <= FP_TEMPLATE_SEARCH_RADIUS; n++) {
								if ((m == FP_TEMPLATE_SEARCH_RADIUS) || (m == (-1) * FP_TEMPLATE_SEARCH_RADIUS)
										|| (n == FP_TEMPLATE_SEARCH_RADIUS)
										|| (n == (-1) * FP_TEMPLATE_SEARCH_RADIUS)) {
									if (P[i + m][j + n] == 1) {
										tc++;
									}
								}
							}
						}

						if ((tc == 1) || (tc == 3)) {
							x = i - origin.x;
							y = j - origin.y;
							r = Math.hypot(x, y);
							if ((x > 0) && (y > 0)) {
								d = Math.atan(y / x);
							} else if ((x < 0) && (y > 0)) {
								d = Math.atan(y / x) - Math.PI;
							} else if ((x < 0) && (y < 0)) {
								d = Math.PI + Math.atan(y / x);
							} else if ((x > 0) && (y < 0)) {
								d = 2 * Math.PI + Math.atan(y / x);
							}
						}

						boolean foundx = false;
						boolean foundy = false;
						for (int m = 7; m <= c; m = m + 6) {
							if (m_arr[m + 4] == 3) {
								if (Math.abs(Math.abs((int) m_arr[m]) - Math.abs(x)) < 4) {
									foundx = true;
								}
								if (Math.abs(Math.abs((int) m_arr[m + 1]) - Math.abs(y)) < 4) {
									foundy = true;
								}
							}
						}

						if ((tc == 1) && (c <= FP_TEMPLATE_MAX_SIZE - 6) && (x != 0) && (y != 0)
								&& ((foundx == false) || (foundy == false))) {

							if (P[i - 1][j + 1] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 135;
							} else if (P[i][j + 1] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 90;
							} else if (P[i + 1][j + 1] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 45;
							} else if (P[i + 1][j] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 0;
							} else if (P[i + 1][j - 1] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 315;
							} else if (P[i][j - 1] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 270;
							} else if (P[i - 1][j - 1] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 225;
							} else if (P[i - 1][j] == 1) {
								m_arr[c++] = x;
								m_arr[c++] = y;
								m_arr[c++] = r;
								m_arr[c++] = d;
								m_arr[c++] = 1;
								m_arr[c++] = 180;
							}
						} else if ((tc >= 3) && (c <= FP_TEMPLATE_MAX_SIZE - 6) && (x != 0) && (y != 0)
								&& ((foundx == false) || (foundy == false))) {
							m_arr[c++] = x;
							m_arr[c++] = y;
							m_arr[c++] = r;
							m_arr[c++] = d;
							m_arr[c++] = 3;
							m_arr[c++] = 0;
						}
					}
					previ = i;
					prevj = j;

					if (((i - origin.x) >= (306 - 4)) && ((i - origin.y) >= (269 - 4))) {
						if (((i - origin.x) <= (306 + 4)) && ((i - origin.y) <= (269 + 4))) {
							JOptionPane.showMessageDialog(null,
									Double.toString(c) + ";" + Integer.toString(i) + ";" + Integer.toString(j),
									"My Point", JOptionPane.PLAIN_MESSAGE);
						}
					}

				}
			}
		}
		m_arr[0] = c;
		return m_arr;
	}

	public String ConvertFingerPrintTemplateDoubleToString(double[] finger) {
		String temp = "";
		for (int i = 0; i <= finger.length - 1; i++) {
			temp = temp + Double.toString(finger[i]) + ";";
		}
		return temp;
	}

	public double[] ConvertFingerPrintTemplateStringToDouble(String finger) {
		double m_finger[] = new double[FP_TEMPLATE_MAX_SIZE];
		int c = -1;
		String m_double = "";
		String temp = "";
		for (int i = 0; i <= finger.length() - 1; i++) {
			temp = Character.toString(finger.charAt(i));
			if (temp.equals(";")) {
				m_finger[c++] = Double.parseDouble(m_double);
			} else {
				m_double = m_double + temp;
			}
		}
		return m_finger;
	}
}