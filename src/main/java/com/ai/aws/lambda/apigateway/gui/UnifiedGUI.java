package com.ai.aws.lambda.apigateway.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ai.aws.lambda.apigateway.model.Lambda;
import com.ai.aws.lambda.apigateway.repository.LambdaRepository;
import com.ai.aws.lambda.apigateway.service.LambdaService;

public class UnifiedGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final LambdaService lambdaService = new LambdaService();
	private final JTextArea displayArea = new JTextArea();

	public UnifiedGUI() {
		
		setTitle("AI + AWS Lambda Manager");
		setSize(700, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Display area
		displayArea.setEditable(false);
		add(new JScrollPane(displayArea), BorderLayout.CENTER);

		// Buttons
		JPanel buttonPanel = new JPanel();

		JButton addButton = new JButton("Add Lambda");
		JButton updateButton = new JButton("Update Lambda");
		JButton deleteButton = new JButton("Delete Lambda");
		JButton listButton = new JButton("List Lambdas");
		JButton exportButton = new JButton("Export to JSON");

		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(listButton);
		buttonPanel.add(exportButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// Actions
		addButton.addActionListener(e -> showLambdaForm(false));
		updateButton.addActionListener(e -> showLambdaForm(true));
		deleteButton.addActionListener(e -> deleteLambda());
		listButton.addActionListener(e -> listLambdas());
		exportButton.addActionListener(e -> {
			lambdaService.exportToJsonFile("lambda_list.json");
			JOptionPane.showMessageDialog(this, "Exported to lambda_list.json");
		});

		setVisible(true);
	}

	private void showLambdaForm(boolean isUpdate) {
		
		JTextField functionNameField = new JTextField();
		JTextField runtimeField = new JTextField();
		JTextField handlerField = new JTextField();
		JTextField roleField = new JTextField();
		JTextField descriptionField = new JTextField();

		JPanel panel = new JPanel(new GridLayout(0, 1));
		if (isUpdate)
			panel.add(new JLabel("Index to Update (0-based):"));
		JTextField indexField = isUpdate ? new JTextField() : null;
		if (isUpdate)
			panel.add(indexField);

		panel.add(new JLabel("Function Name:"));
		panel.add(functionNameField);

		panel.add(new JLabel("Runtime:"));
		panel.add(runtimeField);

		panel.add(new JLabel("Handler:"));
		panel.add(handlerField);

		panel.add(new JLabel("Role:"));
		panel.add(roleField);

		panel.add(new JLabel("Description:"));
		panel.add(descriptionField);

		int result = JOptionPane.showConfirmDialog(this, panel, (isUpdate ? "Update" : "Add") + " Lambda Function",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			String functionName = functionNameField.getText();
			String runtime = runtimeField.getText();
			String handler = handlerField.getText();
			String role = roleField.getText();
			String description = descriptionField.getText();
			String category = LambdaRepository.analyzeText(description);

			Lambda lambda = new Lambda(functionName, runtime, handler, role, description, category);

			if (isUpdate) {
				int index = Integer.parseInt(indexField.getText());
				lambdaService.updateLambda(index, lambda);
			} else {
				lambdaService.addLambda(lambda);
			}

			listLambdas();
		}
	}

	private void deleteLambda() {
		String input = JOptionPane.showInputDialog(this, "Enter index to delete:");
		try {
			int index = Integer.parseInt(input);
			lambdaService.deleteLambda(index);
			listLambdas();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid index");
		}
	}

	private void listLambdas() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (Lambda lambda : lambdaService.getAllLambdas()) {
			builder.append("Index ").append(i++).append(":\n").append(lambda.toString()).append("\n");
		}
		displayArea.setText(builder.toString());
	}
}
