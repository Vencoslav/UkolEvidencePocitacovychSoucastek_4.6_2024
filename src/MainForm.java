import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainForm extends JFrame {
    private JPanel panelMain;
    private JTextField tfPopis;
    private JButton btDalsi;
    private JButton btPredchozi;
    private List<Komponenta> seznam = new ArrayList<>();
    private File selectedFile;
    private int index = 0;

    public MainForm(){
        setContentPane(panelMain);
        setTitle("Evidence počítačových součástek");
        setSize(400,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initMenu();
        nactiSoubor();
        display();

        btDalsi.addActionListener(e->tlacitkoDalsi());
        btPredchozi.addActionListener(e->tlacitkoPrechozi());


    }
    public void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu soubor = new JMenu("Soubor");
        menuBar.add(soubor);

        JMenuItem nacti = new JMenuItem("Načti");
        soubor.add(nacti);
        nacti.addActionListener(e->vyberSoubor());

        JMenuItem uloz = new JMenuItem("Ulož");
        soubor.add(uloz);
        uloz.addActionListener(e->vyberUloz());

        JMenuItem statistiky = new JMenuItem("Statistiky");
        menuBar.add(statistiky);
        statistiky.addActionListener(e ->tlacitkoStatistika());
    }

    public void nactiSoubor(){
        String nazevSouboru = "C:\\Users\\xdole\\OneDrive\\Plocha\\IntelliJ IDEA\\UkolEvidencePocitacovychSoucastek\\vstup";
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(nazevSouboru)))){
            while (sc.hasNextLine()){
                String radek = sc.nextLine();
                String[] rozdelovac = radek.split(",");
                if(rozdelovac.length != 6){
                    throw new RuntimeException("Špatný počet prvků na řádku");
                }
                String popis = rozdelovac[0];
                Integer dodaciDoba = Integer.parseInt(rozdelovac[1]);
                Boolean jeNova = Boolean.parseBoolean(rozdelovac[2]);
                BigDecimal cena = BigDecimal.valueOf(Long.parseLong(rozdelovac[4]));
                LocalDate datum = LocalDate.parse(rozdelovac[5]);
                Integer stav = Integer.parseInt(rozdelovac[3]);

                Komponenta komponenta = new Komponenta(popis,dodaciDoba,jeNova,stav,cena,datum);
                seznam.add(komponenta);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Soubor " + nazevSouboru + "nebyl nalezen " + e.getLocalizedMessage());
        }
    }

    public void tlacitkoDalsi(){
        if(index < seznam.size() - 1){
            index++;
            display();
        }
    }

    public void tlacitkoPrechozi(){
        if(index > 0){
            index--;
            display();
        }
    }

    public void display(){
        Komponenta komponenta = seznam.get(index);

        String stavec = "";
        if(komponenta.getStav() == 1){
            stavec = "výborné";
        } else if(komponenta.getStav() == 2){
            stavec = "dobré";
        } else if(komponenta.getStav() == 3){
            stavec = "použíté";
        }

        tfPopis.setText(komponenta.getPopis() + "," + komponenta.getDodaciDoba() + "," + komponenta.getJeNova() + "," + stavec + "," + komponenta.getCena() + "," + komponenta.getDatum());
    }

    public void vyberSoubor(){
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            selectedFile = fc.getSelectedFile();
            vyberNactenySoubor(selectedFile);
        }
    }

    public void vyberNactenySoubor(File selectedFile){
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile)))){
            while (sc.hasNextLine()){
                String radek = sc.nextLine();
                String[] rozdelovac = radek.split(",");
                if(rozdelovac.length != 6){
                    throw new RuntimeException("Špatný počet prvků na řádku");
                }
                String popis = rozdelovac[0];
                Integer dodaciDoba = Integer.parseInt(rozdelovac[1]);
                Boolean jeNova = Boolean.parseBoolean(rozdelovac[2]);
                BigDecimal cena = BigDecimal.valueOf(Long.parseLong(rozdelovac[4]));
                LocalDate datum = LocalDate.parse(rozdelovac[5]);
                Integer stav = Integer.parseInt(rozdelovac[3]);

                Komponenta komponenta = new Komponenta(popis,dodaciDoba,jeNova,stav,cena,datum);
                seznam.add(komponenta);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Soubor " + selectedFile + "nebyl nalezen " + e.getLocalizedMessage());
        }
    }

    public void tlacitkoStatistika(){
        BigDecimal celkovaCena = BigDecimal.valueOf(0);
        for(Komponenta komponenta : seznam){
            celkovaCena = celkovaCena.add(komponenta.getCena());
        }
        JOptionPane.showMessageDialog(this,"Celkévá cena: " + celkovaCena);
    }

    public void vyberUloz(){
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            selectedFile = fc.getSelectedFile();
            zapisSoubor(selectedFile);
        }
    }

    public void zapisSoubor(File selectedFile){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile))){
            for(Komponenta komponenta : seznam){
                String stavec = "";
                if(komponenta.getStav() == 1){
                    stavec = "výborné";
                } else if(komponenta.getStav() == 2){
                    stavec = "dobré";
                } else if(komponenta.getStav() == 3){
                    stavec = "použíté";
                }

                bw.write(komponenta.getPopis() + "," + komponenta.getDodaciDoba() + "," + komponenta.getJeNova() + "," + stavec + "," + komponenta.getCena() + "," + komponenta.getDatum() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Chyba při zapisování do souboru: " + selectedFile.getAbsolutePath() + ". " + e.getLocalizedMessage(), e);
        }
    }


}

