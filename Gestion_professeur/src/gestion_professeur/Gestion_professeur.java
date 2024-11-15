package gestion_professeur;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gestion_professeur extends JFrame {
    Connexion con = new Connexion();
    Statement pst;
    ResultSet rs;
    JLabel lbtTitre, lblnom, lblemail, lblsexe, lbldepartement, lblrecherche;
    JTextField txtnom, txtemail, txtrecherche;
    JComboBox<String> combosexe, combodepartement;
    JButton btnenregistrer, btnsupprimer, btnmodifier, btnrecherche, btnrefresh;
    JTable table1;
    DefaultTableModel model;
    JScrollPane scroll1;

    public Gestion_professeur() {
        // Fenêtre
        super.setTitle("Enregistrement Des enseignants");
        super.setSize(2000, 900);
        super.setLocationRelativeTo(null);
        super.setResizable(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pn = new JPanel();

        pn.setLayout(null);
        add(pn);
        pn.setBackground(new Color(11111111));

        // Partie titre
        lbtTitre = new JLabel("Enregistrement des Enseignant");
        lbtTitre.setBounds(260, 5, 880, 40);
        lbtTitre.setFont(new Font("Arial", Font.BOLD, 24));
        lbtTitre.setForeground(Color.WHITE);
        pn.add(lbtTitre);

        // Nom et prénom
        lblnom = new JLabel("NOM ET PRENOM");
        lblnom.setBounds(60, 95, 800, 30);
        lblnom.setFont(new Font("Arial", Font.BOLD, 16));
        lblnom.setForeground(Color.WHITE);
        pn.add(lblnom);

        txtnom = new JTextField();
        txtnom.setBounds(250, 95, 250, 30);
        txtnom.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtnom);

        // Email
        lblemail = new JLabel("EMAIL ENSEIGNANT");
        lblemail.setBounds(60, 130, 800, 30);
        lblemail.setFont(new Font("Arial", Font.BOLD, 16));
        lblemail.setForeground(Color.WHITE);
        pn.add(lblemail);

        txtemail = new JTextField();
        txtemail.setBounds(250, 130, 250, 30);
        txtemail.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtemail);

        // Sexe
        lblsexe = new JLabel("SEXE");
        lblsexe.setBounds(60, 170, 800, 30);
        lblsexe.setFont(new Font("Arial", Font.BOLD, 16));
        lblsexe.setForeground(Color.WHITE);
        pn.add(lblsexe);

        combosexe = new JComboBox<>();
        combosexe.setBounds(250, 170, 100, 30);
        combosexe.setFont(new Font("Arial", Font.PLAIN, 14));
        combosexe.addItem("");
        combosexe.addItem("Masculin");
        combosexe.addItem("Feminin");
        pn.add(combosexe);

        // Département
        lbldepartement = new JLabel("DEPARTEMENT");
        lbldepartement.setBounds(60, 205, 800, 30);
        lbldepartement.setFont(new Font("Arial", Font.BOLD, 16));
        lbldepartement.setForeground(Color.WHITE);
        pn.add(lbldepartement);

        combodepartement = new JComboBox<>();
        combodepartement.setBounds(250, 205, 100, 30);
        combodepartement.setFont(new Font("Arial", Font.PLAIN, 14));
        combodepartement.addItem("");
        combodepartement.addItem("Informatique");
        combodepartement.addItem("Mathematique");
        combodepartement.addItem("Physique");
        combodepartement.addItem("Anglais");
        pn.add(combodepartement);

        // Bouton d'enregistrement
        btnenregistrer = new JButton("Enregistrer");
        btnenregistrer.setBounds(100, 250, 150, 30);
        btnenregistrer.setFont(new Font("Arial", Font.BOLD, 15));
        btnenregistrer.setForeground(Color.BLACK);
        btnenregistrer.setBackground(new Color(173, 216, 230));
        btnenregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String nom = txtnom.getText();
                String email = txtemail.getText();
                String sexe = combosexe.getSelectedItem().toString();
                String departement = combodepartement.getSelectedItem().toString();

                String rq = "INSERT INTO enseignant(nom, email, sexe, departement) VALUES(?, ?, ?, ?)";
                try {
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, nom);
                    ps.setString(2, email);
                    ps.setString(3, sexe);
                    ps.setString(4, departement);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Enseignant Enregistré!", null, JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
                refreshTable();
            }
        });
        pn.add(btnenregistrer);

        // Bouton de modification
        btnmodifier = new JButton("Modifier");
        btnmodifier.setBounds(300, 250, 150, 30);
        btnmodifier.setFont(new Font("Arial", Font.BOLD, 15));
        btnmodifier.setForeground(Color.BLACK);
        btnmodifier.setBackground(new Color(173, 216, 230));
        btnmodifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String nom = txtnom.getText();
                String email = txtemail.getText();
                String sexe = combosexe.getSelectedItem().toString();
                String departement = combodepartement.getSelectedItem().toString();

                String rq = "UPDATE enseignant SET nom=?, sexe=?, departement=?  WHERE email=?";
                try {
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, nom);
                    ps.setString(2, sexe);
                    ps.setString(3, departement);
                    ps.setString(4, email);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Enseignant modifié avec succès!", null, JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
                refreshTable();
            }
        });
        pn.add(btnmodifier);

        // Bouton de suppression
        btnsupprimer = new JButton("Supprimer");
        btnsupprimer.setBounds(500, 250, 150, 30);
        btnsupprimer.setFont(new Font("Arial", Font.BOLD, 15));
        btnsupprimer.setForeground(Color.BLACK);
        btnsupprimer.setBackground(Color.RED);
        btnsupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String email = txtemail.getText();

                String rq = "DELETE FROM enseignant WHERE email=?";
                try {
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, email);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Enseignant supprimé avec succès!", null, JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
                refreshTable();
            }
        });
        pn.add(btnsupprimer);

        // Bouton de rafraîchissement
        btnrefresh = new JButton("Rafraîchir");
        btnrefresh.setBounds(700, 250, 150, 30);
        btnrefresh.setFont(new Font("Arial", Font.BOLD, 15));
        btnrefresh.setForeground(Color.BLACK);
        btnrefresh.setBackground(new Color(173, 216, 230));
        btnrefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                clearFields();
                refreshTable();
            }
        });
        pn.add(btnrefresh);

        // Barre de recherche
        lblrecherche = new JLabel("Rechercher:");
        lblrecherche.setBounds(60, 300, 100, 30);
        lblrecherche.setFont(new Font("Arial", Font.BOLD, 16));
        lblrecherche.setForeground(new Color(0, 0, 0));
        pn.add(lblrecherche);

        txtrecherche = new JTextField();
        txtrecherche.setBounds(160, 300, 250, 30);
        txtrecherche.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtrecherche);

        btnrecherche = new JButton("Rechercher");
        btnrecherche.setBounds(420, 300, 150, 30);
        btnrecherche.setFont(new Font("Arial", Font.BOLD, 15));
        btnrecherche.setForeground(Color.BLACK);
        btnrecherche.setBackground(new Color(173, 216, 230));
        btnrecherche.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String recherche = txtrecherche.getText();
                rechercherEnseignant(recherche);
            }
        });
        pn.add(btnrecherche);

        // Tableau
        table1 = new JTable();
        model = new DefaultTableModel();
        table1.setModel(model);
        model.addColumn("Nom");
        model.addColumn("Email");
        model.addColumn("Sexe");
        model.addColumn("Département");

        scroll1 = new JScrollPane(table1);
        scroll1.setBounds(60, 350, 880, 200);
        pn.add(scroll1);

        refreshTable();
        
        // Ecouteur pour les clics sur les lignes de la table
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                txtnom.setText(table1.getValueAt(row, 0).toString());
                txtemail.setText(table1.getValueAt(row, 1).toString());
                combosexe.setSelectedItem(table1.getValueAt(row, 2).toString());
                combodepartement.setSelectedItem(table1.getValueAt(row, 3).toString());
            }
        });
    }
//    pour actualiser la page

    private void refreshTable() {
        model.setRowCount(0); 
        String query = "SELECT * FROM enseignant";
        try {
            pst = con.maConnection().createStatement();
            rs = pst.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String sexe = rs.getString("sexe");
                String departement = rs.getString("departement");
                model.addRow(new Object[]{nom, email, sexe, departement});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
//pour la recherche
    private void rechercherEnseignant(String recherche) {
        model.setRowCount(0); 
        String query = "SELECT * FROM enseignant WHERE nom LIKE ? OR email LIKE ?";
        try {
            PreparedStatement pst = con.maConnection().prepareStatement(query);
            pst.setString(1, "%" + recherche + "%");
            pst.setString(2, "%" + recherche + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String sexe = rs.getString("sexe");
                String departement = rs.getString("departement");
                model.addRow(new Object[]{nom, email, sexe, departement});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la recherche.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtnom.setText("");
        txtemail.setText("");
        combosexe.setSelectedIndex(0);
        combodepartement.setSelectedIndex(0);
    }
    
    public static void main(String[] args) {
        new Gestion_professeur().setVisible(true);
    }
}
