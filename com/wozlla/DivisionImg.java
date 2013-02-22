package com.wozlla;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class DivisionImg {
	private JFrame frame;
	private JMenuBar bar;
	/**
	 * file menu
	 */
	private JMenu file;
	private JMenuItem saveItem;
	private JButton[] jb = new JButton[9];
	private BufferedImage[] subImages = new BufferedImage[9];
	
	private BufferedImage bi;
	private File outputfile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DivisionImg window = new DivisionImg();
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
	public DivisionImg() {
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 612, 519);
		file = new JMenu("File");
		file.setMnemonic('F');
		saveItem = new JMenuItem("Save");
		saveItem.setMnemonic('S');
		file.add(saveItem);
		bindEvent();
		bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(file);
		setImage();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			frame.add(jb[i]);
		}
	}
	
	/*
	 * adding action listener to menu items
	 */
	private void bindEvent() {
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i = 0 ; i < subImages.length ; i++) {
					bi = subImages[i];
					outputfile = new File("gfx/map" + i + ".png");
					try {
						ImageIO.write(bi, "png", outputfile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			return ((BufferedImage) image).getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		return pg.getColorModel().hasAlpha();
	}

	public void setImage() throws IOException {
		URL img = DivisionImg.class.getResource("/gfx/map.png");
		BufferedImage bimg = ImageIO.read(img);
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				BufferedImage wim = bimg.getSubimage(j * w / 3, i * h / 3,
						w / 3, h / 3);
				subImages[count] = wim;
				Image sc = wim.getScaledInstance(frame.getWidth() / 3,
						frame.getHeight() / 3, Image.SCALE_AREA_AVERAGING);
				setupImage(count++, sc);
			}
		}
	}

	private void setupImage(int i, Image wim) {
		jb[i] = new JButton(new ImageIcon(wim));
	}
}