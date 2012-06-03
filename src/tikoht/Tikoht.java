
package tikoht;
import java.sql.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tikomalliVer5.*;

/**
 *
 * @author Tuomas
 */
public class Tikoht {
    
    private Connection con;
    private Malli malli;
    
    public Tikoht(Malli m)
    {
        malli = m;
    }

    private LinkedList tulos; //tähän tulee vaalitulos
    private LinkedList valiTulos; //tähän väliaikainen tulos
    
    
    public Connection haeYhteys() {
        try {
            return SqlYhteydet.muodostaYhteys();
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public LinkedList getTulos() {
        return tulos;
    }
    
    /*lasketaan vaalien lopullinen tulos*/
    public void laskeTulos() {

        //katsotaan onko yhteys kunnossa
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
        try {
           Statement stmtx = con.createStatement();
           stmtx.executeUpdate("UPDATE ehdokas SET vertailuluku = 0");
           stmtx.close();
           Statement stmt = con.createStatement();
           con.setAutoCommit(false);
           tulos = new LinkedList();
           Vaaliliitto vaaliLiitto;
           
           //haetaan vaaliliitot
           ResultSet rs = stmt.executeQuery("Select * from vaaliliitto");

           //käydään kaikki vaaliliitot läpi
           while (rs.next()) {
              
              vaaliLiitto = new Vaaliliitto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4) );
              int aaniMaara = rs.getInt("aanimaara");
              
              int tunnus = rs.getInt("vaaliliittotunnus"); //vaaliliiton tunnus
             
              Ehdokas ehdokas;
              LinkedList ehdokkaat = new LinkedList(); //tähän laitettaan ehdokkaat
              Statement st = con.createStatement();
              //haetaan ehdokkaat jotka kuuluvat kyseiseen vaaliliittoon
              ResultSet res = st.executeQuery("Select * from ehdokas where vaaliliitto = " +tunnus+" AND aanimaara > 0 Order by aanimaara desc" );
              
              //käydään vaaliliiton ehdokaat läpi
              while (res.next()) {
                  ehdokas = new Ehdokas(res.getInt(4),  res.getInt(3), res.getInt(7), res.getInt(5), res.getInt(6));
                   
                  ehdokkaat.addLast(ehdokas);  
              }

              //katsotaan tarviiko ehdokkaita arpoa
          //    ehdokkaat = arvo(ehdokkaat);
              int i = 0;
             
              //lasketaan vertausluvut
              while (i < ehdokkaat.size()) {
                 Ehdokas ehd = (Ehdokas) ehdokkaat.get(i);
                 int vertausLuku = aaniMaara / (i+1);
                 i++;
                 ehd.setVertausluku(vertausLuku);
                 PreparedStatement s = con.prepareStatement("Update ehdokas set vertailuluku = ? WHERE opnro = ?");
                 s.setInt(1, vertausLuku);
                 s.setInt(2, ehd.getOpnro());
                 
                 try {
                 s.executeUpdate(); 
                 }
                 catch (Exception ertt) {
                    ertt.printStackTrace(System.out);
                 }
                 con.commit();
             }
             //tyhjennetään ehdokaslista uutta kierrosta varten
             ehdokkaat.clear();
          }

          //tehdään sama vaalirenkaille
          Vaalirengas vaaliRengas;
          Statement stat = con.createStatement();
          ResultSet rs2 = stat.executeQuery("Select * from vaalirengas");
          while (rs2.next()) {
             vaaliRengas = new Vaalirengas(rs2.getInt(1), rs2.getString(2), rs2.getInt(3));
             int aaniMaara = rs2.getInt("aanimaara");
             System.out.println(aaniMaara);
             int tunnus = rs2.getInt("vaalirengastunnus");
             Ehdokas ehdokas;
             LinkedList ehdokkaat = new LinkedList();
             Statement t = con.createStatement();
             ResultSet res = t.executeQuery("Select * from ehdokas where vaalirengas = " +tunnus+" AND aanimaara > 0 Order by aanimaara desc");
             while (res.next()) {
                ehdokas = new Ehdokas(res.getInt(4), res.getInt(3), res.getInt(7), res.getInt(5), res.getInt(6));
                
                ehdokkaat.addLast(ehdokas);  
             }
            // ehdokkaat = arvo(ehdokkaat);
             int i = 0;
             while (i < ehdokkaat.size()) {
                 Ehdokas ehd = (Ehdokas) ehdokkaat.get(i);
               
                 int vertausLuku = aaniMaara / (i + 1);
                 
                 i++;
                 ehd.setVertausluku(vertausLuku);
                
                 PreparedStatement se = con.prepareStatement("Update ehdokas set vertailuluku = ? WHERE opnro = ?");
                 se.setInt(1, vertausLuku);
                 se.setInt(2, ehd.getOpnro());
                 
                 
                 try {
                 se.executeUpdate(); 
                 }
                 catch (Exception ertt) {
                    ertt.printStackTrace(System.out);
                 }
                 con.commit();
                 
             }
             ehdokkaat.clear();
         }
         Ehdokas ehdokas;
         Statement statem = con.createStatement();
         ResultSet rs3 = statem.executeQuery("Select * from ehdokas where aanimaara > 0");

         //käydään lopuksi vielä ehdokkaat läpi liittoihin tai renkaisin kuulumatto
         //mien varalta
         while (rs3.next()) {
            
            ehdokas = new Ehdokas(rs3.getInt(4), rs3.getInt(3), rs3.getInt(7), rs3.getInt(5), rs3.getInt(6));
            int aanimaara = rs3.getInt("aanimaara");
            System.out.println("Vertailuku: "+ehdokas.getVertausluku());
            System.out.println("opnro: "+ehdokas.getOpnro());
            
            if (ehdokas.getVertausluku() == 0) {
                try {
               int vertausLuku = ehdokas.getAanimaara();
               
               ehdokas.setVertausluku(vertausLuku);
               PreparedStatement sa = con.prepareStatement("Update ehdokas set vertailuluku = ? WHERE opnro = ?");
               sa.setInt(1, vertausLuku);
               sa.setInt(2, ehdokas.getOpnro());
               
                 
               
               sa.executeUpdate(); 
               con.commit();
                }
                catch (Exception ertt) {
                    ertt.printStackTrace(System.out);
                 }
                 
          }  
            tulos.add(ehdokas);  
         }
         //arvotaan lopputulos
      //   tulos = arvo(tulos);
           
        // con.commit();
       //  stmt.close();
           
         }
        catch(SQLException e ){
            e.printStackTrace(System.out);
            try {
               con.rollback();
            }
            catch(SQLException ex) {
                System.out.println("peruutus ei onnistunut");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }

    }
    
    /*annetaan ehdokkaille sijat*/
    public void annaSija(LinkedList l) {
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
        try {
           Statement stmt = con.createStatement();
           con.setAutoCommit(false);
           int sija = 1;  //aloitetaan sijasta 1
           
           //haetaan sija ja päivitetään
           for (int laskuri = 0; laskuri < l.size(); laskuri++ ) {
               Ehdokas ehd = (Ehdokas) l.get(laskuri);
               int tunnus = ehd.getOpnro();
               stmt.executeUpdate("UPDATE ehdokas SET sija = " + sija + " WHERE opnro = " + tunnus + "");
           }
           con.commit();
           stmt.close();
        }
        catch(SQLException e) {
            e.printStackTrace(System.out);
            try {
                con.rollback();
            }
            catch(SQLException ex) {
                
            }
        }
    }


    //arvotaan saman vertausluvun saanneiden paikat
    public LinkedList arvo(LinkedList l) {
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
        Ehdokas ehdokas;
        Ehdokas e;
        LinkedList apulista1 = new LinkedList();
        LinkedList apulista2 = (LinkedList) l.clone();  //kopioidaan tähän parametrina saatulista
        LinkedList apulista3 = new LinkedList();
        try {
           
           //jos lista ei ole tyhjä jatketaan eteenpäin
           if (!apulista2.isEmpty()) {
              ehdokas = (Ehdokas) apulista2.remove();
              //pyöritään silmukassa kunnes lista on tyhjä
              while ( !apulista2.isEmpty()) {
                 e = (Ehdokas) apulista2.remove();
                 
                 //jos ehdokkailla on sama vertausluku tai aanimaara laitetaan listalle
                 if (ehdokas.getVertausluku() == e.getVertausluku()) {
                    
                    //jos lista on tyhjä laitetaan sinne molemmant ehdokkaat
                    if (apulista1.isEmpty()) {
                       apulista1.add(ehdokas);
                       apulista1.add(e);
                       ehdokas = e;
                    }
                    
                    //muuten vain toinen
                    else {
                       apulista1.add(e);
                       ehdokas = e;
                    }
                }
                 
                //muussa tapuksessa suoritetaan arvonta jos lista ei ole tyhjä
                else {
                    if (!apulista1.isEmpty()) {
                        apulista1 = lajittele(apulista1);
                        apulista1 = suoritaArvonta(apulista1);
                        while (!apulista1.isEmpty()) {
                            apulista3.add(apulista1.remove());
                        }
                        apulista1.clear();
                    
                   }
                   else {
                       apulista3.add(e);
                       ehdokas = e;
                   }
                }
            
              }
           }
        }
        catch(Exception ex) {
            ex.printStackTrace();
          
        }
        
        //jos uusilista ei ole tyhjä palautetaan uusi järjestys
        if (!apulista3.isEmpty()) {
             katsoSijat( apulista3);
            return apulista3;
        }
        //jos se jostain syystä on tyhjä palautetaan parametrina saatu lista
        else
           return l;
    }
    
    /*katsotaan ehdokaiden sijat*/
    public void katsoSijat( LinkedList l) {
        
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
       try {
           Statement stmt = con.createStatement();
           con.setAutoCommit(false);
           int seuraava = 0;

           //käydään arvonnat läpi
           ResultSet arvonnat = stmt.executeQuery("SELECT DISTINCT * FROM arvonta");
           while(arvonnat.next()) {
               seuraava++;
           }
           seuraava++;
           for (int i = 0; i < l.size(); i++) {
               Ehdokas ehd = (Ehdokas) l.get(i);
               int tunnus = ehd.getOpnro();
               Statement stmt2 = con.createStatement();
               stmt2.executeUpdate("INSERT INTO arvonta VALUES " + seuraava + "," + tunnus + "," + i + "");
           }
           con.commit();
       }
       catch(SQLException e) {
           try {
              con.rollback(); 
           }
           catch (SQLException ex) {
               
           }
       }
    }
    
    /*arvotaan parametrina saadun listan alkiot satunaiseen järjesteykseen*/
    public LinkedList suoritaArvonta(LinkedList l) {
       Collections.shuffle(l, new Random());
       return l;
    }
    

    /*asetetaan ehdokkaiden vertailuluvuksi 0*/
    public void nollaVertailuLuku() {
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
        //käydään ehdokkaat läpi ja asetetaan vertailuluku nollaksi
        for (int i = 0; i < malli.kerroEhdokkaidenLkm(); i++) {
            try {
                Statement stmtx = con.createStatement();
                Statement stmtx2 = con.createStatement();
                stmtx.executeUpdate("UPDATE ehdokas SET vertailuluku = 0 WHERE ehdokasnro = "+(i+2)+"");
                stmtx2.executeUpdate("UPDATE ehdokas SET aanimaara = 0 WHERE ehdokasnro = "+(i+2)+"");
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    
    
    /*lasketaan väli tilanne samaan tapaan kuin lopullinen tuloskin, mutta ilman arvontoja*/
    public void laskeVali() {

        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        

        
        // vali;
        try {
           Statement stmtx = con.createStatement();
           stmtx.executeUpdate("UPDATE ehdokas SET vertailuluku = 0 WHERE vertailuluku = null");
           stmtx.close();
           Statement stmt = con.createStatement();
           con.setAutoCommit(false);
           tulos = new LinkedList();
           Vaaliliitto vaaliLiitto;
           
           //haetaan vaaliliitot
           ResultSet rs = stmt.executeQuery("Select * from vaaliliitto");
           while (rs.next()) {
              
              vaaliLiitto = new Vaaliliitto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4) );
              int aaniMaara = rs.getInt("aanimaara");
              
              int tunnus = rs.getInt("vaaliliittotunnus"); //vaaliliiton tunnus
             
              Ehdokas ehdokas;
              LinkedList ehdokkaat = new LinkedList(); //tähän laitettaan ehdokkaat
              Statement st = con.createStatement();
              //haetaan ehdokkaat jotka kuuluvat kyseiseen vaaliliittoon
              ResultSet res = st.executeQuery("Select * from ehdokas where vaaliliitto = " +tunnus );
              
              //lasketaan vaaliliiton äänimäärä
              while (res.next()) {
                  ehdokas = new Ehdokas(res.getInt(4), res.getInt(7), res.getInt(3), res.getInt(5), res.getInt(6));
                   
                  ehdokkaat.add(ehdokas);  
              }
              
              int i = 0;
              while (i < ehdokkaat.size()) {
                 Ehdokas ehd = (Ehdokas) ehdokkaat.get(i);
                 int vertausLuku = aaniMaara / (i+1);
                 
                 i++;
                 ehd.setVertausluku(vertausLuku);
                 PreparedStatement s = con.prepareStatement("Update ehdokas set vertailuluku = ? WHERE opnro = ?");
                 s.setInt(1, vertausLuku);
                 s.setInt(2, ehd.getOpnro());
                
                 
                 try {
                 s.executeUpdate(); 
                 }
                 catch (Exception ertt) {
                    ertt.printStackTrace(System.out);
                 }
                 con.commit();
             }
             //tyhjennetään ehdokaslista uutta kierrosta varten
            ehdokkaat.clear();
          }
          //tehdään sama vaalirenkaille
          Vaalirengas vaaliRengas;
          Statement stat = con.createStatement();
          ResultSet rs2 = stat.executeQuery("Select * from vaalirengas");
          while (rs2.next()) {
             vaaliRengas = new Vaalirengas(rs2.getInt(1), rs2.getString(2), rs2.getInt(3));
             int aaniMaara = rs2.getInt("aanimaara");
             System.out.println(aaniMaara);
             int tunnus = rs2.getInt("vaalirengastunnus");
             Ehdokas ehdokas;
             LinkedList ehdokkaat = new LinkedList();
             Statement t = con.createStatement();
             ResultSet res = t.executeQuery("Select * from ehdokas where vaalirengas = " +tunnus);
             while (res.next()) {
                ehdokas = new Ehdokas(res.getInt(4), res.getInt(7), res.getInt(3), res.getInt(5), res.getInt(6));
                ehdokkaat.add(ehdokas);  
             }
             
             int i = 0;
             while (i < ehdokkaat.size()) {
                 Ehdokas ehd = (Ehdokas) ehdokkaat.get(i);
                 int vertausLuku = aaniMaara / (i + 1);
                 i++;
                 ehd.setVertausluku(vertausLuku);
                 PreparedStatement se = con.prepareStatement("Update ehdokas set vertailuluku = ? WHERE opnro = ?");
                 se.setInt(1, vertausLuku);
                 se.setInt(2, ehd.getOpnro());
                 
                 try {
                 se.executeUpdate(); 
                 }
                 catch (Exception ertt) {
                    ertt.printStackTrace(System.out);
                 }
                 con.commit();
                 
             }
             ehdokkaat.clear();
         }
         Ehdokas ehdokas;
         Statement statem = con.createStatement();
         ResultSet rs3 = statem.executeQuery("Select * from ehdokas");

         while (rs3.next()) {
            
            ehdokas = new Ehdokas(rs3.getInt(4), rs3.getInt(7), rs3.getInt(3), rs3.getInt(5), rs3.getInt(6));
            int aanimaara = rs3.getInt("aanimaara");
           
            if (ehdokas.getVertausluku() == 0) {
               int vertausLuku = ehdokas.getAanimaara();
               ehdokas.setVertausluku(vertausLuku);
               PreparedStatement sa = con.prepareStatement("Update ehdokas set vertailuluku = ? WHERE opnro = ?");
               sa.setInt(1, vertausLuku);
               sa.setInt(2, ehdokas.getOpnro());
               
                 
               try {
               sa.executeUpdate(); 
                }
                catch (Exception ertt) {
                    ertt.printStackTrace(System.out);
                 }
                 con.commit();
          }  
             
         }
        
         
           
         con.commit();
         stmt.close();
           
         }
        catch(SQLException e ){
            e.printStackTrace(System.out);
            try {
               con.rollback();
            }
            catch(SQLException ex) {
                System.out.println("peruutus ei onnistunut");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
        }

    }
    
    /*tulostetaan tulos*/
    public void nayta(){
        
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
        try {
            Statement stmt = con.createStatement();
           
            //haetaan ehdokkaat ja tulostetaan
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT * FROM ehdokas");
            while (rs.next()) {
               System.out.println(rs.toString());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /*arvotaan ehdokkaille ehdokas numerot*/
    public void ennakkoAanestys() {
        
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        try {
           PreparedStatement stmt = con.prepareStatement("SELECT * FROM ehdokas");
           con.setAutoCommit(false);
           ResultSet rs = stmt.executeQuery();
           int laskuri = 0;
           
           //katsotaan kuinka monta ehdokasta on
           while (rs.next()) {
               laskuri++;
           }
           rs.close();
           LinkedList numerot = new LinkedList();
           int ehdokasnumero = 2;
           
           //laitetaan numerot listalle
           for (int k = 1; k <= laskuri; k++) {
               numerot.add(ehdokasnumero);
               ehdokasnumero++;
           }
           
           PreparedStatement stmt2 = con.prepareStatement("UPDATE ehdokas SET ehdokasnro = ? WHERE opnro = ?" );
           //arvotaan järjestys
           Collections.shuffle(numerot, new Random());
           ResultSet res = stmt.executeQuery();
           
           //asetetaan ehdokkaille ehdokasnumerot
           while (res.next()) {
               System.out.println("Onko täällä mitään?");
               int tunnus = res.getInt(4);
               ehdokasnumero = (Integer) numerot.removeFirst();
               stmt2.setInt(1, ehdokasnumero); 
               stmt2.setInt(2, tunnus);
               stmt2.executeUpdate();
               
           }
           
           stmt2.close();
           
           res.close();
           con.commit();
        }

        catch(SQLException e) {
            e.printStackTrace(System.out);
           try {
             con.rollback();
           }
           catch(SQLException ex) {
               System.out.println("ei voi peruuttaa");
           }
        }
        catch(Exception virhe) {
            virhe.printStackTrace();
        }
        
    }
    
    /*lajitellaan lista vertain -oliota käyttäen*/
    public LinkedList lajittele(LinkedList l) {
        
        try {
           Vertain v = new Vertain();
           Collections.sort(l,v);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    
    /*lisätään ääni ehdokkaalle ja ehdokkaan vaaliliitolle ja vaalirenkaalle*/
    public void lisaaAani( int ehdokasNumero, int ehdokkaanOpnro, int aanestyspaikkaTunnus, int aanestajanOpnro) {
        
        try {
            if (con == null || con.isClosed()) {
                   con = SqlYhteydet.muodostaYhteys();
               }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
        SqlYhteydet.setSearchPathTo();
        
        
        try {
           Statement st = con.createStatement();
       
           con.setAutoCommit(false);

           //lisätään ehdokaalle ääni
           boolean onkoAiempiaAania = onkoAiempiaAania(ehdokasNumero);
           if (!onkoAiempiaAania) {
               PreparedStatement updateEhdokas = con.prepareStatement("UPDATE ehdokas SET aanimaara = 1 where ehdokasnro = ?");
               updateEhdokas.setInt(1, ehdokasNumero);
               updateEhdokas.executeUpdate();
           } else {
               PreparedStatement updateEhdokas = con.prepareStatement("UPDATE ehdokas SET aanimaara = aanimaara + 1 where ehdokasnro = ?");
               updateEhdokas.setInt(1, ehdokasNumero);
               updateEhdokas.executeUpdate();
           }

           
           //haetaan vaaliliito
           ResultSet ehd = st.executeQuery("select * from ehdokas WHERE ehdokasNro = " + ehdokasNumero );
           ehd.next();
           int vl = ehd.getInt("vaaliliitto");
           int aanimaara;
          
           //katsotaan tarviiko vaaliliitolle lisätä ääni
           if (vl != 0) {
               Statement a = con.createStatement();
               ResultSet aanim = a.executeQuery("select aanimaara from vaaliliitto where vaaliliittotunnus = " + vl);
               aanim.next();
               aanimaara = aanim.getInt("aanimaara");
               if (aanimaara != 0) {
                   Statement st1 = con.createStatement();
                   st1.executeUpdate("UPDATE vaaliliitto SET aanimaara = aanimaara + 1 WHERE vaaliliittotunnus = " + vl);
                   con.commit();
               }
               else {
                   Statement st3 = con.createStatement();
                   st3.executeUpdate("UPDATE vaaliliitto SET aanimaara = 1 WHERE vaaliliittotunnus = " + vl);
                   con.commit();
               }
            
           }   
           int vr = ehd.getInt("vaalirengas");
           
           //katsotaan tarviiko vaalirenkaalle lisätä ääni
           if (vr != 0) {
               Statement b = con.createStatement();
               ResultSet aania = b.executeQuery("select aanimaara from vaalirengas where vaalirengastunnus = " + vr);
               aania.next();
               aanimaara = aania.getInt("aanimaara");
               if (aanimaara != 0) {
                   Statement st2 = con.createStatement();
                   st2.executeUpdate("UPDATE vaalirengas SET aanimaara = aanimaara + 1 WHERE vaalirengastunnus = " + vr);
                   con.commit();
               }
               else {
                  Statement st4 = con.createStatement();
                  st4.executeUpdate("UPDATE vaalirengas SET aanimaara = 1 WHERE vaalirengastunnus = " + vr);
                  con.commit();
               }
            
           } 
  
           ehd.close();
           st.close();
            
           //päivitetään ehdokkaan tilanne 
           PreparedStatement updateAanestaja = con.prepareStatement("INSERT INTO aanestaja VALUES ( ? , ?)");
           updateAanestaja.setInt(1, aanestajanOpnro);
           updateAanestaja.setInt(2, aanestyspaikkaTunnus);
           updateAanestaja.executeUpdate();
           con.commit();

           //katsotaan missä ehdokas on äänestänyt
           try {
               boolean onkoEhdostaAanestettyTaalla = kokeileOnkoEhdokaspaikkaa(ehdokkaanOpnro, aanestyspaikkaTunnus);
               con.commit();
               if (!onkoEhdostaAanestettyTaalla) {
                   PreparedStatement insertEhdokasPaikka = con.prepareStatement("INSERT INTO ehdokas_paikka VALUES ( ? , 1 , ? )");
                   insertEhdokasPaikka.setInt(1, aanestyspaikkaTunnus);
                   insertEhdokasPaikka.setInt(2, ehdokkaanOpnro);
                   insertEhdokasPaikka.executeUpdate();
               } else {
                  PreparedStatement updateEhdokasPaikka = con.prepareStatement("UPDATE ehdokas_paikka SET aanten_lkm = aanten_lkm + 1 WHERE aanestyspaikkatunnus = ? and opnro = ?;");
                  updateEhdokasPaikka.setInt(1, aanestyspaikkaTunnus);     
                  updateEhdokasPaikka.setInt(2, ehdokkaanOpnro);
                  updateEhdokasPaikka.executeUpdate();
               }
               
           } catch (SQLException ex) {
               ex.printStackTrace(System.out);
           
           } 
           
           con.commit();
        
        }
        catch(SQLException e) {
            e.printStackTrace(System.out);
            try {
                con.rollback();
            }
            catch(SQLException ex) {
                
            }
        }
        catch(Exception poikkeus) {
            poikkeus.printStackTrace(System.out);
        }
    }

    /*katsotaan onko tiettyä ehdokasta äänestetty tietyssä ehdokaspaikassa*/
    private boolean kokeileOnkoEhdokaspaikkaa(int opNro, int aptunnus) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            SqlYhteydet.setSearchPathTo();
            
            int montako = 0;
            
            PreparedStatement onkoAaniaEhdokaspaikassa = con.prepareStatement("SELECT aanten_lkm FROM ehdokas_paikka where aanestyspaikkatunnus = ? and opnro = ?");
            onkoAaniaEhdokaspaikassa.setInt(1, aptunnus);     
            onkoAaniaEhdokaspaikassa.setInt(2, opNro);
            
            ResultSet rs = onkoAaniaEhdokaspaikassa.executeQuery();
            while (rs.next()) {
                montako = rs.getInt(1);
            }
            if (montako == 0) {
                return false;
            } else {
                return true;
            }
            

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        }
    }

    /*katsotaan onko ehdokkaalla aiempia ääniä*/
    private boolean onkoAiempiaAania(int ehdokasnumero) {
        try {
            try {
                if (con == null || con.isClosed()) {
                       con = SqlYhteydet.muodostaYhteys();
                   }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            SqlYhteydet.setSearchPathTo();
            
            int montako = 0;
            
            PreparedStatement onkoAania = con.prepareStatement("SELECT aanimaara FROM ehdokas where ehdokasnro = ?");
            onkoAania.setInt(1, ehdokasnumero);
            ResultSet rs = onkoAania.executeQuery();
            while (rs.next()) {
                montako = rs.getInt(1);
            }
            if (montako == 0) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return false;
        }
        
    }
    
     
}
