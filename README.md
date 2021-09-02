# MyCalculator
사칙 연산과 지우기 같은 기본적인 기능이 있는 계산기에 사용자가 직접 수식을 입력할 수 있는 입력란도 추가되어 있다.

### 목차  
1. 설계  
    - 설계 및 출력화면  
        - 설계  
        - 출력화면  
2. 코드 설명
    1) class CalculatorMain  
    2) class CalculatorEvent  
        - 전체코드  
        - 필드  
        - 코드흐름 설명  
        - 코드 흐름 및 기능  


# 1. **설계**

![image](https://user-images.githubusercontent.com/56223389/131798032-d5a64caa-ee88-461e-932e-9021b47a9131.png)

## 설계 및 출력화면

### 설계

1. Frame
    - BoarderLayout을 사용하였다.
    - North에는 Jpanel p1을 배치하고, Center에는 JPanel p2를 배치하였다.
2. JPanel p1
    - BoarderLayout을 사용하였다.
    - JTextField 컴포넌트로 수식을 직접 입력받을 수 있다.
    - JLabel 컴포넌트로 계산기 버튼을 이용해 수식을 표시할 수도 있고, 수식 결과를 보여주기도 한다.
3. JPanel p2
    - GridBagLayout을 사용하였다.
    - 계산기에 필요한 버튼들이 있다.

### 출력화면

1. 숫자 및 연산자 버튼

    버튼을 통해 수식을 입력할 수 있다.

    버튼을 통한 수식은 두번째 줄(JLabel)에 표시된다.

2. = 버튼

    두번째 줄에 수식의 결과를 표시한다.

    ![image](https://user-images.githubusercontent.com/56223389/131798192-d4adcfa0-1508-4aa2-88c1-130f9845f650.png)

3. DEL 버튼

    JLabel에 표시된 수식의 맨 뒤부터 한글자씩 지운다.

    ![image](https://user-images.githubusercontent.com/56223389/131798241-a650abca-fc53-4ced-8756-af59f5bcee83.png)

4. JTextField

    키보드를 통해 직접 수식을 입력할 수 있다.

    직접 수식은 첫번째 줄(JTextField)에 입력된다.

5. 직접입력= 버튼

    JTextField에 표시된 수식의 결과를 두번째 줄(JLabel)에 표시한다.

    ![image](https://user-images.githubusercontent.com/56223389/131798342-06c752da-c2c4-4f19-859a-3709ca4194de.png)

6. C 버튼

    첫번째 줄(JTextField)과 두번째 줄(JLabel)의 수식을 모두 초기화한다.

    ![image](https://user-images.githubusercontent.com/56223389/131798373-6cc31ebe-b88c-4ab9-9950-52c0c9798611.png)

# 2. **코드 설명**

## 1. **class CalculatorMain**

- 코드

    ```java
    public class CalculatorMain {

        public static void main(String[] args)
        {
            // CalculatorEvent
            CalculatorEvent calculator = new CalculatorEvent();
        }
    }
    ```

main함수를 포함하고, CalculatorEvent 객체를 생성하는 클래스이다..

## 2. **class CalculatorEvent**

### 전체코드

- 코드

    ```java
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    import javax.swing.*;

    import javax.script.ScriptEngine;
    import javax.script.ScriptEngineManager;
    import javax.script.ScriptException;

    public class CalculatorEvent {
        JFrame f;
        JPanel p1, p2;
        JLabel tf;
        JTextField tf2;
        String expression = "0"; // 수식

        // 그리드백 계산기
        public CalculatorEvent(){

            f = new JFrame ("Grid Calculator - 김경륜 IT공학전공 1814996");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLayout (new BorderLayout ());
            f.setSize (400, 500);

            // 1. 텍스트 부분
            p1 = new JPanel ();
            p1.setLayout(new BorderLayout());
            tf = new JLabel(expression);
            tf2 = new JTextField (20);
            // *** 글씨 크기 크게 폰트 설정 ***
            Font font = new Font("Serif", Font.PLAIN, 30);
            // 폰트 붙이기
            tf.setFont(font);
            tf2.setFont(font);
            p1.add("North", tf2);
            p1.add ("Center",tf);

            // 2. 버튼 부분
            p2 = new JPanel ();

            // 그리드백 객체
            GridBagLayout gridbag = new GridBagLayout();

            // 그리드백 제어 객체
            GridBagConstraints constraint = new GridBagConstraints();

            p2.setLayout (gridbag);

            // Component가 격자보다 작을 때의 처리 지정
            // - BOTH :격자 크기에 맞춤
            constraint.fill = GridBagConstraints.BOTH;
            //Component가 크기를 비율로 지정
            // - 0 : Container 크기가 변해도 원래 크기 유지
            // - 0 이외의 값 : 같은 행에 있는 Component간의 비율 계산
            constraint.weightx = 1;
            constraint.weighty = 1;

            // 버튼 text
            font = new Font("Serif", Font.PLAIN, 20);
            String[] bTitle = {
                    "C", // 0
                    "DEL", // 1
                    "next", // 2
                    "직접입력=", // 3
                    ".", // 4
                    "/",  // 5
                    "*",  // 6
                    "-",  // 7
                    "7",  // 8
                    "8",  // 9
                    "9",  // 10
                    "+",  // 11
                    "4",  // 12
                    "5",  // 13
                    "6", // 14
                    "(",  // 15
                    "1",  // 16
                    "2",  // 17
                    "3", // 18
                    ")", // 19
                    "next", // 20
                    "0", // 21
                    "=" // 22
            };

            // 버튼 배열
            JButton[] b = new JButton[bTitle.length];

            // 버튼 생성
            for (int i = 0; i < b.length; i++){
                b[i] = new JButton(bTitle[i]);
                b[i].setFont(font);
                b[i].addActionListener(new BtnActionListener());

            }

            // GridBag 사용해서 버튼 만들기
            boolean next = false;
            for (int i = 0; i < b.length; i++){

                if (b[i].getText() == "next"){
                    next = true;
                    continue;
                }

                if (next){
                    // 2칸으로 한다
                    constraint.gridwidth = 2;
                    next = false;
                }
                // 마지막행 줄바꿈 설정
                if (i % 4 == 3)
                    constraint.gridwidth = GridBagConstraints.REMAINDER;

                // *** '=' 버튼(마지막행, 마지막열) 2칸 설정 ***
                if (i == b.length-1)
                    constraint.gridwidth = 2;

                // 버튼에 설정 붙이기
                gridbag.setConstraints(b[i], constraint);
                // 패널에 버튼 붙이기
                p2.add(b[i]);

                // 다음 버튼부터는 원래대로 1칸
                constraint.gridwidth = 1;

            }

            // 프레임 설정
            f.add ("North",p1);
            f.add ("Center", p2);
            f.setVisible (true);

        } // CalculatorEvent

        private class BtnActionListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                JButton btn = (JButton)e.getSource();
                String b = btn.getText();

                // C 버튼 : 모든 수식 초기화
                if(b.equals("C")){
                    expression = "0";
                    tf2.setText("");
                }
                // DEL 버튼 : 마지막 글자 하나 지우기
                else if(b.equals("DEL")){
                    if(expression.length() > 0){
                        expression = expression.substring(0, expression.length()-1);
                    }
                    if(expression.length() == 0){
                        expression = "0";
                    }
                }
                // = 버튼 : 수식 계산
                else if(b.equals("=")){
                    // 자바스크립트의 eval 사용
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    try {

                        // eval()을 통해 expression을 계산한 뒤, string으로 변환
                        String tmp_result= (engine.eval(expression)).toString();

                        // 결과 표시
                        tf.setText(tmp_result);
                        expression = tmp_result;
                    }
                    catch (ScriptException ex) {
                        // 계산 결과 오류시 메시지 창 띄우기
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(f, "수식이 올바르지 않습니다.");
                    }
                }
                //직접입력= 버튼 : 수식 계산
                else if(b.equals("직접입력=")){
                    // 자바스크립트의 eval 사용
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    try {
                        expression = (tf2.getText()).toString();

                        // eval()을 통해 expression을 계산한 뒤, string으로 변환
                        String tmp_result= (engine.eval(expression)).toString();

                        // 결과 표시
                        tf.setText(tmp_result);
                        expression = tmp_result;
                    }
                    catch (ScriptException ex) {
                        // 계산 결과 오류시 메시지 창 띄우기
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(f, "수식이 올바르지 않습니다.");
                    }
                }

                // 그 외 숫자 및 연산자 버튼
                else{
                    if(expression == "0"){
                        expression = "";
                    }
                    expression += b;

                }

                // 수식 결과를 실시간으로 표시하기
                tf.setText(expression);
            }
        } // BtnActionListener

    }
    ```

### 필드

- frame, panel
- JTextFiled tf2: 첫번째 줄, 직접 수식을 입력하는 부분
- JLabel tf: 두번째 줄, 수식 및 수식 결과가 표시되는 부분
- String expression: 사용자가 입력한 수식

### 코드흐름 설명

1. CalculatorEvent ()

    생성자 함수이다.

    JFrame, JPanel, JButton을 만든다.

    - JPanel p1에는 JTextField와 JLabel이 붙는다.
        - JTextField: 수식을 직접 입력하는 부분이다.
        - JLabel : 버튼을 통해 만들어진 수식 및 수식 결과를 표시하는 부분이다. 첫째 줄의 수식 결과는 ‘직접입력=’ 버튼을 눌러야 하고, 둘째 줄의 수식 결과는 ‘=’버튼을 눌러야 한다. 둘 다 수식 결과는 둘째 줄에 표시된다.
    - JPanel p2: GridBagLayout을 통해 JButton들을 배치한다.
    - 버튼의 종류는 bTitle의 원소들과 같다.
        - next는 계산기 버튼에 포함되는 것이 아니라 배열에서 next의 다음 인덱스의 원소가 버튼으로 배치 시 가로로 2칸을 차지하게 만드는 데에 쓰인다.
2. class BtnActionListener implements ActionListener

    버튼들의 이벤트를 처리한다.

    - 코드

        ```java
        private class BtnActionListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                JButton btn = (JButton)e.getSource();
                String b = btn.getText();

                // C 버튼 : 모든 수식 초기화
                if(b.equals("C")){
                    expression = "0";
                    tf2.setText("");
                }
                // DEL 버튼 : 마지막 글자 하나 지우기
                else if(b.equals("DEL")){
                    if(expression.length() > 0){
                        expression = expression.substring(0, expression.length()-1);
                    }
                    if(expression.length() == 0){
                        expression = "0";
                    }
                }
                // = 버튼 : 수식 계산
                else if(b.equals("=")){
                    // 자바스크립트의 eval 사용
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    try {

                        // eval()을 통해 expression을 계산한 뒤, string으로 변환
                        String tmp_result= (engine.eval(expression)).toString();

                        // 결과 표시
                        tf.setText(tmp_result);
                        expression = tmp_result;
                    }
                    catch (ScriptException ex) {
                        // 계산 결과 오류시 메시지 창 띄우기
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(f, "수식이 올바르지 않습니다.");
                    }
                }
                //직접입력= 버튼 : 수식 계산
                else if(b.equals("직접입력=")){
                    // 자바스크립트의 eval 사용
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    try {

                        expression = (tf2.getText()).toString();

                        // eval()을 통해 expression을 계산한 뒤, string으로 변환
                        String tmp_result= (engine.eval(expression)).toString();

                        // 결과 표시
                        tf.setText(tmp_result);
                        expression = tmp_result;
                    }
                    catch (ScriptException ex) {
                        // 계산 결과 오류시 메시지 창 띄우기
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(f, "수식이 올바르지 않습니다.");
                    }
                }

                // 그 외 숫자 및 연산자 버튼
                else{
                    if(expression == "0"){
                        expression = "";
                    }
                    expression += b;

                }

                // 수식 결과를 실시간으로 표시하기
                tf.setText(expression);
            }
        } // BtnActionListener
        ```

### 코드 흐름 및 기능

- C
    - 첫째 줄과 둘째 줄 즉, 모든 줄의 수식 초기화
- DEL
    - 둘째 줄의 마지막 글자 하나 지우기
- =
    - 둘째 줄의 수식 계산 결과를 둘째 줄에 표시한다.
    - 계산은 자바스크립트의 eval() 함수를 사용하였다.
    - 수식이 올바르지 않으면(예: 2+3**4) "수식이 올바르지 않습니다." 라는 메시지 창을 띄운다.
- 직접입력=
    - 첫째 줄의 수식 계산 결과를 둘째 줄에 표시한다.
    - 계산은 자바스크립트의 eval() 함수를 사용하였다.
    - 수식이 올바르지 않으면(예: 2+3**4) "수식이 올바르지 않습니다." 라는 메시지 창을 띄운다.
- 그 외 숫자 및 연산자 버튼
    - 처음부터 시작하면 표시된 ‘0’을 지우고 입력된 버튼의 값으로 시작하게 한다.
    - 처음이 아니면 계속 수식에 버튼의 값을 더한다.
- 마지막에는 JLabel에 수식 또는 수식 결과를 실시간으로 표시하게 한다.
