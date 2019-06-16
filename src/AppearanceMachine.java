import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AppearanceMachine implements ActionListener {

    private static JFrame jFrame;

    private JPanel jPanelForButton;
    private JPanel jPanel1;
    JPanel jPanel2;

    JLabel jLabel1;//JLabel суммы в автомате
    static JTextArea jTextArea1;

    JLabel jLabel2;//JLabel заказа
    static JTextArea jTextArea2;

    JLabel jLabel3;//JLabel внесенной суммы
    static JTextArea jTextArea3;

    JLabel jLabel4;//JLabel сдачи
    static JTextArea jTextArea4;

    static JButton jButtonBeginAndEnd;//кнопка начать/закончить
    static JButton jButtonTechnicalSupport;//кнопка вызова технической поддержки
    static JButton jButtonGiveNewspaper;//кнопка выдачи заказа
    static JButton jButtonGiveChange;//кнопка выдачи сдачи

    static Runnable textArea2;
    static Thread thread2;


    static boolean fl = false;//работа программы, по умолчанию - не работает
    static boolean runMachine = true;//работа машины

    static boolean buttonGiveNewspaper = false;//по умолчанию не активна, пока очередной пользователей не сделает заказ
    static boolean buttonGiveChange = false;//по умолчанию не активна, пока не потребуется выдать сдачу пользователю
    static boolean buttonTechnicalSupport = false;//по умолчанию не активна, пока не потребуется тех.поддержка
    static NewspaperMachine newspaperMachine;

    static MyDrawPanel drawPanel;
    Color xColor = Color.RED;
    static Runnable runnableCircle;
    static Thread threadCircle;

    static int flError = 0;//если 1, не выдает газету; если 2, не выдает сдачу

    public void go() {
        jFrame = new JFrame("Машина по продаже газет");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jLabel1 = new JLabel("Денег в автомате");
        jTextArea1 = new JTextArea(2, 3);
        JScrollPane scrollPane1 = new JScrollPane(jTextArea1);
        jTextArea1.setLineWrap(true);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea1.setEditable(false);

        jLabel2 = new JLabel("Заказ");
        jTextArea2 = new JTextArea(2, 3);
        JScrollPane scrollPane2 = new JScrollPane(jTextArea2);
        jTextArea2.setLineWrap(true);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea2.setEditable(false);

        jLabel3 = new JLabel("Внесенная сумма");
        jTextArea3 = new JTextArea(2, 3);
        JScrollPane scrollPane3 = new JScrollPane(jTextArea3);
        jTextArea3.setLineWrap(true);
        scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea3.setEditable(false);

        jLabel4 = new JLabel("Сдача");
        jTextArea4 = new JTextArea(2, 3);
        JScrollPane scrollPane4 = new JScrollPane(jTextArea4);
        jTextArea4.setLineWrap(true);
        scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea4.setEditable(false);

        jPanel1 = new JPanel();
        jPanel1.setLayout(new GridLayout(4, 2));
        jPanel1.add(jLabel1);
        jPanel1.add(scrollPane1);
        jPanel1.add(jLabel2);
        jPanel1.add(scrollPane2);
        jPanel1.add(jLabel3);
        jPanel1.add(scrollPane3);
        jPanel1.add(jLabel4);
        jPanel1.add(scrollPane4);

        jButtonBeginAndEnd = new JButton("Начать работу");
        jButtonBeginAndEnd.addActionListener(this);
        jButtonGiveChange = new JButton("Выдать сдачу");
        jButtonGiveChange.addActionListener(this);
        jButtonGiveChange.setEnabled(buttonGiveChange);//по умолчанию не активна, пока не вписать в строку сумму сдачи
        jButtonGiveNewspaper = new JButton("Выдать газету");
        jButtonGiveNewspaper.addActionListener(this);
        jButtonGiveNewspaper.setEnabled(buttonGiveNewspaper);
        jButtonTechnicalSupport = new JButton("Вызвать тех.поддержку");
        jButtonTechnicalSupport.addActionListener(this);
        jButtonTechnicalSupport.setEnabled(buttonTechnicalSupport);//кнопка активна, если машина не сломалась

        jPanelForButton = new JPanel();
        jPanelForButton.setLayout(new GridLayout(4, 1));
        jPanelForButton.add(jButtonBeginAndEnd);
        jPanelForButton.add(jButtonGiveChange);
        jPanelForButton.add(jButtonGiveNewspaper);
        jPanelForButton.add(jButtonTechnicalSupport);

        drawPanel = new MyDrawPanel();

        jFrame.getContentPane().add(BorderLayout.CENTER, jPanel1);
        jFrame.getContentPane().add(BorderLayout.EAST, jPanelForButton);
        jFrame.getContentPane().add(BorderLayout.NORTH, drawPanel);

        jFrame.setSize(1000, 500);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        newspaperMachine = new NewspaperMachine(200);

        new AppearanceMachine().go();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Начать работу":
                JOptionPane.showMessageDialog(jFrame, "Автомат начал свою работу", "Сообщение", 1);
                jTextArea1.setText("\n\n\n" + newspaperMachine.getMoney());
                fl = true;
                jButtonBeginAndEnd.setText("Закончить работу");

                runnableCircle = new RunnableForCircle();
                textArea2 = new textArea2();

                threadCircle = new Thread(runnableCircle);
                thread2 = new Thread(textArea2);

                threadCircle.start();
                thread2.start();
                break;
            case "Закончить работу":
                jButtonBeginAndEnd.setText("Начать работу");
                JOptionPane.showMessageDialog(jFrame, "Автомат закончил свою работу", "Сообщение", 1);
                threadCircle.stop();
                thread2.stop();

                xColor = Color.red;
                jFrame.repaint();
                fl = false;
                break;
            case "Вызвать тех.поддержку":
                runMachine = true;
                buttonTechnicalSupport = false;
                jButtonTechnicalSupport.setEnabled(buttonTechnicalSupport);
                JOptionPane.showMessageDialog(jFrame, "Тех.поддержка исправила неисправность в работе автомата", "Сообщение", 1);
                if (flError == 1) {
                    buttonGiveNewspaper = true;
                    jButtonGiveNewspaper.setEnabled(buttonGiveNewspaper);
                }
                if (flError == 2) {
                    buttonGiveChange = true;
                    jButtonGiveChange.setEnabled(buttonGiveChange);
                }
                break;
            case "Выдать газету":
                JOptionPane.showMessageDialog(jFrame, "Газета выдана успешно", "Сообщение", 1);
                buttonGiveNewspaper = false;
                jButtonGiveNewspaper.setEnabled(buttonGiveNewspaper);
                break;
            case "Выдать сдачу":
                JOptionPane.showMessageDialog(jFrame, "Сдача выдана успешно", "Сообщение", 1);
                buttonGiveChange = false;
                jButtonGiveChange.setEnabled(buttonGiveChange);
                break;
        }
    }

    public class textArea2 implements Runnable {

        @Override
        public void run() {
            chooseNewspaper();
        }
    }

    private void chooseNewspaper() {
        while (fl && runMachine) {
            int number = new Random().nextInt(Newspaper.values().length) + 1;
            switch (number) {
                case 1:
                    runMachineWithNumber(Newspaper.PROMOTIONAL_AND_INFORMATIONAL);
                    break;
                case 2:
                    runMachineWithNumber(Newspaper.ENTERTAINING);
                    break;
                case 3:
                    runMachineWithNumber(Newspaper.DAILY);
                    break;
                case 4:
                    runMachineWithNumber(Newspaper.WEEKLY);
                    break;
            }
        }
    }

    private void runMachineWithNumber(Newspaper newspaper) {
        int change = 0;
        jTextArea2.setText("\n\n\n" + "Ожидает заказа...");
        jTextArea3.setText("\n\n\n");
        jTextArea4.setText("\n\n\n");
        try {
            thread2.sleep(new Random().nextInt(5000) + 5000);
            StringBuffer order = new StringBuffer(newspaper.getTitle());
            jTextArea2.setText("\n\n\n" + order.toString() + " газета");
            thread2.sleep(2000);
            int randomSum = ((new Random().nextInt(newspaper.getPrice() / 10) * 2) + newspaper.getPrice() / 10) * 10;
            jTextArea3.setText("\n\n\n" + String.valueOf(randomSum));
            thread2.sleep(1000);
            newspaperMachine.setMoney(newspaperMachine.getMoney() + randomSum);
            jTextArea1.setText("\n\n\n" + String.valueOf(newspaperMachine.getMoney()));
            thread2.sleep(2000);
            JOptionPane.showMessageDialog(jFrame, "Выдать пользователю газету и нажать OK", "Сообщение", 1);
            buttonGiveNewspaper = true;
            jButtonGiveNewspaper.setEnabled(buttonGiveNewspaper);
            int randNumber = new Random().nextInt(45);
            if (randNumber < 20) {
                JOptionPane.showMessageDialog(jFrame, "Автомат зажевал газету\n" +
                        "Пожалуйста, вызовете тех.поддержку", "Ошибка", JOptionPane.ERROR_MESSAGE);
                flError = 1;
                runMachine = false;
                buttonTechnicalSupport = true;
                jButtonTechnicalSupport.setEnabled(buttonTechnicalSupport);
                buttonGiveNewspaper = false;
                jButtonGiveNewspaper.setEnabled(buttonGiveNewspaper);
                while (!buttonGiveNewspaper) {
                    try {
                        thread2.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            while (buttonGiveNewspaper) {
                thread2.sleep(500);
            }
            if (randomSum > newspaper.getPrice()) {
                do {
                    StringBuffer changes = new StringBuffer(JOptionPane.showInputDialog("Введите в поле нужную сумму и выдайте пользователю сдачу\nПодсказка:" + String.valueOf(randomSum - newspaper.getPrice())));
                    change = Integer.parseInt(changes.toString());
                } while (change != (randomSum - newspaper.getPrice()));
                if (change > 0) {
                    jTextArea4.setText("\n\n\n" + String.valueOf(change));
                    newspaperMachine.setMoney(newspaperMachine.getMoney() - change);
                    jTextArea1.setText("\n\n\n" + String.valueOf(newspaperMachine.getMoney()));
                } else
                    jTextArea4.setText("\n\n\n-");
                buttonGiveChange = true;
                jButtonGiveChange.setEnabled(buttonGiveChange);
                errorGiveChange();
                while (buttonGiveChange) {
                    thread2.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void errorGiveChange() {
        int numberErrorGiveChange = new Random().nextInt(38);
        if (numberErrorGiveChange < 19) {
            JOptionPane.showMessageDialog(jFrame, "Автомат не выдает сдачу\n" +
                    "Пожалуйста, вызовете тех.поддержку", "Ошибка", JOptionPane.ERROR_MESSAGE);
            flError = 2;
            runMachine = false;
            buttonTechnicalSupport = true;
            jButtonTechnicalSupport.setEnabled(buttonTechnicalSupport);
            buttonGiveChange = false;
            jButtonGiveChange.setEnabled(buttonGiveChange);
            while (!buttonGiveChange) {
                try {
                    thread2.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public class RunnableForCircle implements Runnable {

        @Override
        public void run() {
            try {
                circle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(xColor);
            g.fillOval(1, 1, 8, 8);
        }
    }

    public void circle() throws InterruptedException {
        for (; ; ) {
            xColor = Color.gray;
            jFrame.repaint();
            Thread.sleep(200);
            xColor = Color.green;
            jFrame.repaint();
            Thread.sleep(1700);
        }
    }

}