package com.prd.ConnectedTeam.userOperation;


import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import com.prd.ConnectedTeam.entity.Punteggio;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import com.prd.ConnectedTeam.error.ErrorPage;
import com.prd.ConnectedTeam.gamesRules.GameList;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;


@Route("StatisticUser")
@HtmlImport("style.html")
@StyleSheet("frontend://stile/style.css")
@PageTitle("ConnecTeam")
public class StatisticUser extends VerticalLayout {


    private PartitaRepository partitaRepository;
    private Account account;

    private String punti="";
    private GameList gameList;


    public StatisticUser() {

        try {
            getStyle().set("padding", "0px");
            NavBar navBar = new NavBar();
            add(navBar);
            HorizontalLayout superMain = new HorizontalLayout();
            superMain.getStyle().set("margin-top","100px");
            superMain.getStyle().set("width","100%");
            setAlignItems(Alignment.CENTER);
            HorizontalLayout main = new HorizontalLayout();
            main.addClassName("main-chart");

            account = (Account) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user");
            partitaRepository = (PartitaRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("partitaRepository");
            gameList = (GameList) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("gameList");

            HorizontalLayout statisticheLayout = new HorizontalLayout();
            statisticheLayout.addClassName("statisticheLayout");
            Div panelSinistro = new Div();
            VerticalLayout layoutNomiGiochi = new VerticalLayout();
            panelSinistro.add(layoutNomiGiochi);

            List<String> gameNames = gameList.getGameNames();
            Label giochi = new Label("Giochi");
            giochi.getStyle().set("font-size","30px");
            layoutNomiGiochi.add(giochi);

            Tabs tabs = new Tabs();

            gameNames.forEach(s -> {
                Image image1 = new Image("frontend/img/"+s+".jpeg", "alt");
                image1.setWidth("24px");
                image1.setHeight("24px");
                Button nome = new Button(s,image1);
                HorizontalLayout layout1 = new HorizontalLayout(nome);
                layout1.getStyle().set("alignItems", "center");
                Tab tab = new Tab(layout1);
                nome.addClassName("buttonGameName");
                nome.addClickListener(buttonClickEvent -> {
                    onGameClick(nome.getText(),superMain,main);
                });
                tabs.add(tab);
                layoutNomiGiochi.add(tabs);
            });

            Image image1 = new Image("frontend/img/"+"logo"+".png", "alt");
            image1.setWidth("24px");
            image1.setHeight("24px");

           /* for(int i=0; i<10;i++){
                Image image2 = new Image("frontend/img/"+"pc"+".png", "alt");
                image2.setWidth("24px");
                image2.setHeight("24px");
                Button nome = new Button(""+i,image2);
                HorizontalLayout layout2 = new HorizontalLayout(nome);
                layout2.getStyle().set("alignItems", "center");
                Tab tab2 = new Tab(layout2);
                nome.addClassName("buttonGameName");
                nome.addClickListener(buttonClickEvent -> {
                    onGameClick(nome.getText(),superMain,main);
                });
                tabs.add(tab2);
                layoutNomiGiochi.add(tabs);
            }//prova piu giochi*///prova view più giochi

            tabs.setOrientation(Tabs.Orientation.VERTICAL);
            tabs.setHeight("315px");
            tabs.setWidth("150px");

            Div panelDestro = new Div();
            statisticheLayout.add(panelSinistro, panelDestro);
            main.add(statisticheLayout);
            main.getStyle().set("padding", "10px");
            superMain.add(main);
            onGameClick(gameNames.get(0),superMain,main);
            add(superMain);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            removeAll();
            getStyle().set("background-color","white");
            ErrorPage errorPage = new ErrorPage();
            add(errorPage);
        }
    }

    private void onGameClick(String gioco, HorizontalLayout superMain,HorizontalLayout main){
        superMain.removeAll();
        superMain.add(main);
        List<Partita> partite= partitaRepository.cercaPerAccountEGioco(account, gioco);
        System.out.println(partite.size()+"   "+account.getNome());
        System.out.println("paritite");
        partite.forEach(System.out::println);

        LinkedHashMap<String, Integer> punteggiMiei= new LinkedHashMap<>();
        VerticalLayout verticalLayout = new VerticalLayout();

        if(partite==null || partite.size()==0){
            superMain.add(verticalLayout);
            superMain.add(chart(partite,gioco,punteggiMiei));
            verticalLayout.setWidth("300px");
            verticalLayout.setHeight("300px");
        }else{

            Partita last = partite.get(partite.size()-1);

            List<Punteggio> punteggiLastParita= last.getArray();

            /*List<Punteggio> punteggi= punteggiLastParita.stream().filter(punteggio ->
                    punteggio.getAccount().getId()==account.getId()).collect(Collectors.toList());*///??


            String lastData=new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(partitaRepository.lastPartita(account).getTimestamp());
            List<Partita> partitas = partitaRepository.cercaByAccount(account);


            Label numeroPartiteGiocate = new Label("Partite giocate: "+partitas.size());
            numeroPartiteGiocate.getStyle().set("font-size","20px");
            Label ultimaPartita = new Label("Ultima partita: "+lastData);
            ultimaPartita.getStyle().set("font-size","20px");
            Label punteggioUltimaPartita = new Label("Punteggio ultima partita: "+punteggiLastParita.get(punteggiLastParita.size()-1).getPunti());
            punteggioUltimaPartita.getStyle().set("font-size","20px");
            verticalLayout.add(numeroPartiteGiocate,ultimaPartita,punteggioUltimaPartita);
            superMain.add(verticalLayout);
            verticalLayout.getStyle().set("margin-top","100px");
            partite.forEach(partita -> {
                for(Punteggio p: partita.getArray()){
                    if(p.getAccount().getId()==account.getId()){
                        String data=new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(partita.getTimestamp());
                        punteggiMiei.put(data, p.getPunti());
                        break;
                    }
                }
            });
            superMain.add(chart(partite,gioco,punteggiMiei));
        }
    }

    private HorizontalLayout chart(List<Partita> partite,String gioco, LinkedHashMap<String, Integer> punteggi) {
        if(partite==null || partite.size()==0){
            HorizontalLayout horizontalLayoutChart = new HorizontalLayout();
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setAlignItems(Alignment.CENTER);
            verticalLayout.getStyle().set("margin-top","100px");
            Label info = new Label("Non hai effettuato partite a "+gioco);
            info.getStyle().set("font-size","40px");
            verticalLayout.add(info);
            Button goPlay = new Button("Vai alla lista dei giochi",new Icon(VaadinIcon.PLAY));
            goPlay.addClassName("goPlay");
            goPlay.addClickListener(buttonClickEvent -> {
                UI.getCurrent().navigate(HomeView.class);
            });
            verticalLayout.add(goPlay);
            horizontalLayoutChart.add(verticalLayout);
            return horizontalLayoutChart;

        }else {

            HorizontalLayout horizontalLayoutChart = new HorizontalLayout();
            Chart chart = new Chart(ChartType.PIE);
            chart.getStyle().set("height","385px");
            chart.getStyle().set("margin-right","200px");
            chart.getStyle().set("border-radius","10%");
            Configuration conf = chart.getConfiguration();
            conf.setTitle("Punteggi");
            conf.setSubTitle(gioco);
            Tooltip tooltip = new Tooltip();
            conf.setTooltip(tooltip);
            PlotOptionsPie plotOptions = new PlotOptionsPie();
            plotOptions.setAllowPointSelect(true);
            plotOptions.setCursor(Cursor.POINTER);
            //plotOptions.setShowInLegend(true);
            conf.setPlotOptions(plotOptions);
            DataSeries series = new DataSeries();
            punteggi.forEach((s, integer) -> {
                series.add(new DataSeriesItem(s, integer));
            });
            conf.setSeries(series);
            chart.setVisibilityTogglingDisabled(true);
            horizontalLayoutChart.add(chart);
            return horizontalLayoutChart;

        }


    }

}
