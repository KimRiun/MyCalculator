//// 참고사이트
//// 폰트 관련:
////     https://asterisco.tistory.com/145
//// 그리드백 관련:
////     https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=javaking75&logNo=140189054193
//// 버튼 배열 관련:
////     https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=javaking75&logNo=140157952404


package com.example.lib.oop_package.homework;

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
            // = 버튼 : 둘째 줄의 수식 계산
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
                    System.out.println(expression);
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
                catch (NullPointerException ex) {
                    // 계산 결과 오류시 메시지 창 띄우기
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "직접입력이 비어있습니다.");

                    expression = "0";

                }
            }



            // 그 외 숫자 및 연산자 버튼
            else{
                if(expression == "0"){
                    expression = "";
                }
                expression += b;

            }

            // 수식 또는 수식 결과를 실시간으로 표시하기
            tf.setText(expression);
        }
    } // BtnActionListener

}