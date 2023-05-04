package com.Gangasagar.namahatta_katha.Model;

public class qnaModel {
    private String qnaTitle, qna, qnaIcon;


    public qnaModel() {
    }

    public qnaModel(String qnaTitle, String qna, String qnaIcon) {
        this.qnaTitle = qnaTitle;
        this.qna = qna;
        this.qnaIcon = qnaIcon;
    }

    public String getQnaTitle() {
        return qnaTitle;
    }

    public void setQnaTitle(String qnaTitle) {
        this.qnaTitle = qnaTitle;
    }

    public String getQna() {
        return qna;
    }

    public void setQna(String qna) {
        this.qna = qna;
    }

    public String getQnaIcon() {
        return qnaIcon;
    }

    public void setQnaIcon(String qnaIcon) {
        this.qnaIcon = qnaIcon;
    }
}
