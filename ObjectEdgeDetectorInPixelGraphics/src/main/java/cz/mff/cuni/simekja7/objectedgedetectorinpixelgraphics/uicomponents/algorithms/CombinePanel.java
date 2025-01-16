/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers.AlgorithmCombiner;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel class for encapsulating AlgorithmCombiner.
 * Displays components to change parameters of both algorithms and their combination.
 * @author simek.jan
 */
public class CombinePanel extends AlgorithmPanel {
    
    private static JComboBox<String> leftDropdown;
    private static JComboBox<String> rightDropdown;
    private static JPanel leftPanel;
    private static JPanel rightPanel;

    /**
     * Constructor that creates panel to set up AlgorithmCombiner.
     */
    public CombinePanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        JCheckBox andCheckbox = new JCheckBox("AND results", true) ;
        JCheckBox orCheckbox = new JCheckBox("OR results", false);
        JCheckBox weightedCheckbox = new JCheckBox("Weighted results", false);
        weightedCheckbox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        topPanel.add(andCheckbox);
        topPanel.add(orCheckbox);
        topPanel.add(weightedCheckbox);
        JSlider divider = new JSlider(0, 100, 50);
        JLabel algLabel1 = new JLabel("Canny Edge: 50%");
        JLabel algLabel2 = new JLabel("Sobel: 50%");
        algLabel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        topPanel.add(algLabel1);
        topPanel.add(divider);
        topPanel.add(algLabel2);
        JLabel andThresholdLabel = new JLabel("Result threshold: 1");
        JSlider andThreshold = new JSlider(1, 254, 1);
        topPanel.add(andThresholdLabel);
        topPanel.add(andThreshold);
        
        // Initial is AND, so these are not visible
        divider.setVisible(false);
        algLabel1.setVisible(false);
        algLabel2.setVisible(false);
        andThresholdLabel.setVisible(false);
        andThreshold.setVisible(false);
        
        
        andCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (andCheckbox.isSelected() || weightedCheckbox.isSelected()) {
                    orCheckbox.setSelected(false);
                    weightedCheckbox.setSelected(false);
                } else {
                    andCheckbox.setSelected(true);
                }
                AlgorithmCombiner.mode = AlgorithmCombiner.COMBINE_MODE.AND;
                divider.setVisible(false);
                algLabel1.setVisible(false);
                algLabel2.setVisible(false);
                andThresholdLabel.setVisible(false);
                andThreshold.setVisible(false);
            }
        });

        orCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (orCheckbox.isSelected() || weightedCheckbox.isSelected()) {
                    andCheckbox.setSelected(false);
                    weightedCheckbox.setSelected(false);
                } else {
                    orCheckbox.setSelected(true);
                }
                AlgorithmCombiner.mode = AlgorithmCombiner.COMBINE_MODE.OR;
                divider.setVisible(false);
                algLabel1.setVisible(false);
                algLabel2.setVisible(false);
                andThresholdLabel.setVisible(false);
                andThreshold.setVisible(false);
            }
        });
        
        weightedCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (andCheckbox.isSelected() || orCheckbox.isSelected()) {
                    andCheckbox.setSelected(false);
                    orCheckbox.setSelected(false);
                } else {
                    weightedCheckbox.setSelected(true);
                }
                AlgorithmCombiner.mode = AlgorithmCombiner.COMBINE_MODE.WEIGHTED;
                divider.setVisible(true);
                algLabel1.setVisible(true);
                algLabel2.setVisible(true);
                andThresholdLabel.setVisible(true);
                andThreshold.setVisible(true);
            }
        });
        
        divider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int part1 = divider.getValue();
                int part2 = 100 - part1;

                algLabel1.setText(leftDropdown.getSelectedItem() + ": " + part1 + "%");
                algLabel2.setText(rightDropdown.getSelectedItem() + ": " + part2 + "%");
                
                AlgorithmCombiner.alpha1 = ((double)divider.getValue()) / 100.0;
                AlgorithmCombiner.alpha2 = (100.0 - ((double)divider.getValue())) / 100.0;
            }
        });
        
        andThreshold.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int threshold = andThreshold.getValue();
                andThresholdLabel.setText("Result threshold: " + threshold);
                
                AlgorithmCombiner.threshold = threshold;
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
        leftPanel = new CannyPanel();
        rightPanel = new SobelPanel();

        algorithmPanel.add(leftPanel);
        algorithmPanel.add(rightPanel);

        leftDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAlgorithmPanel((String) leftDropdown.getSelectedItem(), leftPanel);
                
                int part1 = divider.getValue();
                int part2 = 100 - part1;

                algLabel1.setText(leftDropdown.getSelectedItem() + ": " + part1 + "%");
                algLabel2.setText(rightDropdown.getSelectedItem() + ": " + part2 + "%");
            }
        });

        rightDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAlgorithmPanel((String) rightDropdown.getSelectedItem(), rightPanel);
                
                int part1 = divider.getValue();
                int part2 = 100 - part1;

                algLabel1.setText(leftDropdown.getSelectedItem() + ": " + part1 + "%");
                algLabel2.setText(rightDropdown.getSelectedItem() + ": " + part2 + "%");
            }
        });
        
        // Inital algorithms
        updateAlgorithmPanel((String) leftDropdown.getSelectedItem(), leftPanel);
        updateAlgorithmPanel((String) rightDropdown.getSelectedItem(), rightPanel);
    }

    /**
     * Private method that handles changes of algorithm in given algorithm panel.
     * @param selection 
     */
    private void updateAlgorithmPanel(String selection, JPanel panelToChange) {
        panelToChange.removeAll();
        switch (selection) {
            case "Canny Edge":
                panelToChange.add(new CannyPanel());
                break;
            case "Sobel":
                panelToChange.add(new SobelPanel());
                break;
            case "Laplacian":
                panelToChange.add(new LaplacianPanel());
                break;
            case "SIFT":
                panelToChange.add(new SiftPanel());
                break;
            case "Custom CE":
                panelToChange.add(new CustomCannyPanel());
                break;
        }
        panelToChange.revalidate();
        panelToChange.repaint();
    }

    /**
     * Runs AlgorithmCombiner with chosen algorithms for given image.
     * @param inputImage
     * @return Result of AlgorithmCombiner in image.
     */
    @Override
    public BufferedImage runAlgorithm(BufferedImage inputImage) {
        AlgorithmCombiner.alg1 = ((AlgorithmPanel) leftPanel.getComponent(0)).getAlgorithm();
        AlgorithmCombiner.alg2 = ((AlgorithmPanel) rightPanel.getComponent(0)).getAlgorithm();
        
        BufferedImage result = new AlgorithmCombiner().run(inputImage);
            
        return result;
    }

    /**
     * Returns a new instance of AlgorithmCombiner.
     * @return New AlgorithmCombiner instance.
     */
    @Override
    public EdgeAlgorithm getAlgorithm() {
        return new AlgorithmCombiner();
    }
}
