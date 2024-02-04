package com.alshuk.patterns;

//Паттерн Шаблонный метод

public class TemplateMethodApp {
    public static void main(String[] args) {
        WebPageTemplate homePage = new HomePageTemplate();
        homePage.showPage();

        System.out.println("___________________-");

        WebPageTemplate newsPage = new NewsPageTemplate();
        newsPage.showPage();
    }

    static class HomePageTemplate extends WebPageTemplate {
        @Override
        public void showContent() {
            System.out.println("Welcome!");
        }
    }

    static class NewsPageTemplate extends WebPageTemplate {
        @Override
        public void showContent() {
            System.out.println("News!");
        }
    }



    //Суть паттерна состоит в том, чтобы разделить алгоритм на несколько методов, вынести какую-то общую повторяемую
    // логику работы алгоритма в отдельный суперкласс в виде абстрактного метода, а затем наследоваться от данного класа,
    // дополняя данный метод какими-либо нужными действиями.
    // Избавляемся от дублирования кода в нескольких классах с похожим поведением, но отличающися в деталях, путем
    // концентрации общего алгоритма действия в суперклассе.

    static abstract class WebPageTemplate {
        public final void showPage() {
            System.out.println("Header");
            showContent();
            System.out.println("Footer");
        }

        public abstract void showContent();
    }

    //В данном случае у обеих страниц есть одинаковый хеадер и футер, но индивидуальный контент отличается.
}
