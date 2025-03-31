import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class StockTrading {
    private JFrame frame;
    private JTextArea marketDataArea;
    private JTextArea portfolioArea;
    private JTextField stockSymbolField;
    private JTextField quantityField;
    private final Map<String, Double> marketData;
    private final Map<String, Integer> portfolio;

    public StockTrading() {
        marketData = new HashMap<>();
        portfolio = new HashMap<>();
        initializeMarketData();
        initializeUI();
    }

    private void initializeMarketData() {
        marketData.put("AAPL", 150.0);
        marketData.put("GOOGL", 2800.0);
        marketData.put("AMZN", 3400.0);
        marketData.put("TSLA", 800.0);
    }

    private void initializeUI() {
        frame = new JFrame("Stock Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        marketDataArea = new JTextArea(10, 30);
        marketDataArea.setEditable(false);
        updateMarketDataDisplay();

        portfolioArea = new JTextArea(10, 30);
        portfolioArea.setEditable(false);
        updatePortfolioDisplay();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Stock Symbol:"));
        stockSymbolField = new JTextField();
        inputPanel.add(stockSymbolField);
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        inputPanel.add(buyButton);
        inputPanel.add(sellButton);

        buyButton.addActionListener(e -> tradeStock(true));
        sellButton.addActionListener(e -> tradeStock(false));

        frame.add(new JScrollPane(marketDataArea), BorderLayout.NORTH);
        frame.add(new JScrollPane(portfolioArea), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateMarketDataDisplay() {
        StringBuilder sb = new StringBuilder("Market Data:\n");
        for (Map.Entry<String, Double> entry : marketData.entrySet()) {
            sb.append(entry.getKey()).append(": $").append(entry.getValue()).append("\n");
        }
        marketDataArea.setText(sb.toString());
    }

    private void updatePortfolioDisplay() {
        StringBuilder sb = new StringBuilder("Portfolio:\n");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" shares\n");
        }
        portfolioArea.setText(sb.toString());
    }

    private void tradeStock(boolean isBuying) {
        String symbol = stockSymbolField.getText().toUpperCase();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!marketData.containsKey(symbol)) {
            JOptionPane.showMessageDialog(frame, "Stock not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isBuying) {
            portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
        } else {
            if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity) {
                JOptionPane.showMessageDialog(frame, "Not enough shares to sell.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            portfolio.put(symbol, portfolio.get(symbol) - quantity);
            if (portfolio.get(symbol) == 0) {
                portfolio.remove(symbol);
            }
        }

        updatePortfolioDisplay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StockTrading::new);
    }
}
