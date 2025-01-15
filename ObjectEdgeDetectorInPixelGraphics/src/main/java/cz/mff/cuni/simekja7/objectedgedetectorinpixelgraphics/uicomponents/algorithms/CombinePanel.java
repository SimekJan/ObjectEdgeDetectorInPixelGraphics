/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author simek.jan
 */
public class CombinePanel extends JPanel {
    private JComboBox<String> leftDropdown;
    private JComboBox<String> rightDropdown;
    private JPanel leftPanel;
    private JPanel rightPanel;

    public CombinePanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        JCheckBox andCheckbox = new JCheckBox("AND results", true) ;
        JCheckBox orCheckbox = new JCheckBox("OR results", false);
        orCheckbox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        topPanel.add(andCheckbox);
        topPanel.add(orCheckbox);
        JSlider divider = new JSlider(0, 100, 50);
        JLabel algLabel1 = new JLabel("Canny Edge: 50%");
        JLabel algLabel2 = new JLabel("Canny Edge: 50%");
        topPanel.add(algLabel1);
        topPanel.add(divider);
        topPanel.add(algLabel2);
        
        andCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (andCheckbox.isSelected()) {
                    orCheckbox.setSelected(false);
                } else {
                    andCheckbox.setSelected(true);
                }
            }
        });

        orCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (orCheckbox.isSelected()) {
                    andCheckbox.setSelected(false);
                } else {
                    orCheckbox.setSelected(true);
                }
            }
        });
        
        divider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int part1 = divider.getValue();
                int part2 = 100 - part1;

                algLabel1.setText("Canny Edge: " + part1 + "%");
                algLabel2.setText("Canny Edge: " + part2 + "%");
            }
        });
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        
        leftDropdown = new JComboBox<>(new String[] { "Canny Edge", "Sobel", "Laplacian", "SIFT", "Custom CE" });
        rightDropdown = new JComboBox<>(new String[] { "Canny Edge", "Sobel", "Laplacian", "SIFT", "Custom CE" });
        rightDropdown.setSelectedItem("Sobel");

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new GridLayout(1, 2));
        dropdownPanel.add(leftDropdown);
        dropdownPanel.add(rightDropdown);

        centerPanel.add(dropdownPanel, BorderLayout.NORTH);

        JPanel algorithmPanel = new JPanel();
        algorithmPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(algorithmPanel, BorderLayout.CENTER);
        leftPanel = new CannyPanel(false);
        rightPanel = new SobelPanel(false);

        algorithmPanel.add(leftPanel);
        algorithmPanel.add(rightPanel);

        leftDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLeftPanel((String) leftDropdown.getSelectedItem());
            }
        });

        rightDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRightPanel((String) rightDropdown.getSelectedItem());
            }
        });
        
        JButton runButton = new JButton("Run");
        add(runButton, BorderLayout.SOUTH);
    }

    private void updateLeftPanel(String selection) {
        leftPanel.removeAll();
        switch (selection) {
            case "Canny Edge":
                leftPanel.add(new CannyPanel(false));
                break;
            case "Sobel":
                leftPanel.add(new SobelPanel(false));
                break;
            case "Laplacian":
                leftPanel.add(new LaplacianPanel(false));
                break;
            case "SIFT":
                leftPanel.add(new SiftPanel(false));
                break;
            case "Custom CE":
                leftPanel.add(new CustomCannyPanel(false));
                break;
        }
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    private void updateRightPanel(String selection) {
        rightPanel.removeAll();
        switch (selection) {
            case "Canny Edge":
                rightPanel.add(new CannyPanel(false));
                break;
            case "Sobel":
                rightPanel.add(new SobelPanel(false));
                break;
            case "Laplacian":
                rightPanel.add(new LaplacianPanel(false));
                break;
            case "SIFT":
                rightPanel.add(new SiftPanel(false));
                break;
            case "Custom CE":
                rightPanel.add(new CustomCannyPanel(false));
                break;
        }
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
