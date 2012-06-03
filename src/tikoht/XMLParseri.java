package tikoht;

import com.sun.jndi.toolkit.url.Uri;
import java.io.File;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import java.sql.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import tikomalliVer5.Malli;
import tikomalliVer5.SqlKyselyt;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tuomas
 */
public class XMLParseri {
    
    private Malli malli;
  //  private SqlKyselyt kyselija;
    
    public XMLParseri(Malli m) {
        malli = m;
    //    kyselija = new SqlKyselyt(malli);
    }
    
 
   /*
    * XML-tiedoston ehdokkaiden lisäämisen oltava muotoa:
    * <Ehdokkaat>
<Ehdokas>
  <opnro>11135</opnro>
  <nimi>Jani Joiku</nimi>
  <vaaliliitto>Masut</vaaliliitto>
  <vaalirengas></vaalirengas>
</Ehdokas>
<Ehdokas>
  <opnro>11223</opnro>
  <nimi>Jani Ojojsu</nimi>
  <vaaliliitto>Masut</vaaliliitto>
  <vaalirengas>Nusut</vaalirengas>
</Ehdokas>
<Ehdokas>
  <opnro>32775</opnro>
  <nimi>Ja6hdshs iku</nimi>
  <vaaliliitto></vaaliliitto>
  <vaalirengas></vaalirengas>
</Ehdokas>
<Ehdokas>
  <opnro>56999</opnro>
  <nimi>Jussi jaa</nimi>
  <vaaliliitto>Masut</vaaliliitto>
  <vaalirengas></vaalirengas>
</Ehdokas>
<Ehdokas>
  <opnro>56994</opnro>
  <nimi>Julma heisno</nimi>
  <vaaliliitto>Massssssut</vaaliliitto>
  <vaalirengas></vaalirengas>
</Ehdokas>
</Ehdokkaat>
    */
  public void luoEhdokas(String url) {
      try {
         DocumentBuilderFactory docBuilderF = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = docBuilderF.newDocumentBuilder();
         Document doc;
         doc = docBuilder.parse(url);
          
          //tarvittavat muuttujat
         String opnro = "";
         String nimi = "";
         String vaaliliitto = "";
         String vaalirengas = "";
         doc.getDocumentElement ().normalize ();
         
         NodeList ehdokasLista = doc.getElementsByTagName("Ehdokas"); //haetaan ehdokkaat
         int maara = ehdokasLista.getLength();
         
         //puöritään silmukassa niin kauan kuin on ehdokkatioa
         for (int i = 0; i < maara; i++) {
             Node ehdokasSolmu = ehdokasLista.item(i);
             
             if(ehdokasSolmu.getNodeType() == Node.ELEMENT_NODE){
                Element ehdokas = (Element)ehdokasSolmu;
                
                //haetaan opiskelija numero ja muokataan se sopivaan muotoon
                NodeList opnroLista = ehdokas.getElementsByTagName("opnro");
                Element opnroElement = (Element)opnroLista.item(0);
                NodeList tekstiOpnro = opnroElement.getChildNodes();
                opnro = (String)tekstiOpnro.item(0).getNodeValue().trim();
              
                //sama kuin edellä
                NodeList nimiLista = ehdokas.getElementsByTagName("nimi");
                Element nimiElement = (Element)nimiLista.item(0);
                NodeList tekstiNimi = nimiElement.getChildNodes();
                nimi = (String)tekstiNimi.item(0).getNodeValue().trim();
              
                //sama kuin edellä mutta ktasotaan onko tämä tyhjä
                NodeList vaaliliittoLista = ehdokas.getElementsByTagName("vaaliliitto");
             //  if (vaaliliittoLista.getLength() > 0) {
                try {
                   Element vpElement = (Element)vaaliliittoLista.item(0);
                   NodeList tekstiVp = vpElement.getChildNodes();
                   vaaliliitto = (String)tekstiVp.item(0).getNodeValue().trim();
                } catch (NullPointerException ex) {
                    vaaliliitto = "null";
                }

                    //   }
              
                //sam kuin edellä
                NodeList vaalirengasLista = ehdokas.getElementsByTagName("vaalirengas");
              //  if (vaalirengasLista.getLength() > 0) {
              try {  
                   Element vrElement = (Element)vaalirengasLista.item(0);
                   NodeList tekstiVr = vrElement.getChildNodes();
                   vaalirengas = (String)tekstiVr.item(0).getNodeValue().trim();
                   } catch (NullPointerException ex) {
                    vaalirengas = "null";
                }
              // }
                
    
               int onro = Integer.parseInt(opnro);
               
               //kutsutaan lisäysmetodia
            //   lisaaEhdokas(onro, nimi, vaaliliitto, vaalirengas);
               malli.lisaaParsittuEhdokas(onro, nimi, vaaliliitto, vaalirengas);
            
               //tyhjennentään seuraavaa kierrosta varten
               opnro = "";
               nimi = "";
               vaalirengas = "";
               vaaliliitto = "";
  
            }
        }
      }
      catch(Exception e) {
         e.printStackTrace(System.out);
          
      }
      
   }
  /*
   * XML-tiedostossa haetaan vain vaaliliiton nimi ja vaalirenkaan nimi, joka myöhemmin
   * muutetaan vaalirenkaan tunnukseksi.
   * XML-tiedoston tulee siis olla muotoa:
   * <Vaaliliitot>
   *   <Vaaliliitto>
   *     <nimi>
   *        Pellet
   *     </nimi>
   *     <vaalirengas>
   *         Tykit
   *     </vaalirengas>
   *   </Vaaliliitto>
   *   <Vaaliliitto>
   *     <nimi>
   *        Hessut
   *     </nimi>
   *      <vaalirengas>
   *         Nykit
   *     </vaalirengas>
   *   </Vaaliliitto>
   * </Vaaliliitot>
   */
    public void luoVaaliliitto(String url) {
      try {
         DocumentBuilderFactory docBuilderF = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = docBuilderF.newDocumentBuilder();
         Document doc;
         doc = docBuilder.parse(url);
          
       //  String vlnro = "";
         String nimi = "";
         String vaalirengas = "";
         doc.getDocumentElement ().normalize ();
         NodeList vpLista = doc.getElementsByTagName("Vaalipiiri");
         int maara = vpLista.getLength();
         for (int i = 0; i < maara; i++) {
             Node vpSolmu = vpLista.item(i);
             if(vpSolmu.getNodeType() == Node.ELEMENT_NODE){
                Element vaaliliitto = (Element)vpSolmu;           
                            
                NodeList nimiLista = vaaliliitto.getElementsByTagName("nimi");
                Element nimiElement = (Element)nimiLista.item(0);
                NodeList tekstiNimi = nimiElement.getChildNodes();
                nimi = (String)tekstiNimi.item(0).getNodeValue().trim();
              
              
                NodeList vaalirengasLista = vaaliliitto.getElementsByTagName("vaalirengas");
                if (vaalirengasLista.getLength() > 0) {
                   Element vrElement = (Element)vaalirengasLista.item(0);
                   NodeList tekstiVr = nimiElement.getChildNodes();
                   vaalirengas = (String)tekstiVr.item(0).getNodeValue().trim();
               }
        
               
               if (vaalirengas.isEmpty())
                   vaalirengas = "null";
               //int lnro = Integer.parseInt(vlnro);
               malli.lisaaParsittuVaaliliitto(nimi, vaalirengas);
           //    lisaaVaaliliitto(lnro, nimi, vaaliliitto, vaalirengas);
            //   vlnro = "";
               nimi = "";
               vaalirengas = "";
               
  
            }
        }
      }
      catch(Exception e) {
         
          
      }
      
   }
    /*
   * XML-tiedostossa haetaan vain vaalirenkaan nimi. XML-tiedoston tulee siis olla muotoa
   * <Vaalirenkaat>
   *   <Vaalirengas>
   *     <nimi>
   *        Tarmot
   *     </nimi>
   *   </Vaalirengas>
   *   <Vaalirengas>
   *     <nimi>
   *        Pehkot
   *     </nimi>
   *   </Vaalirengas>
   * </Vaalirenkaat>
   */
   public void luoVaalirengas(String url) {
      try {
         DocumentBuilderFactory docBuilderF = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = docBuilderF.newDocumentBuilder();
         Document doc;
         doc = docBuilder.parse(url);
          
         String vrnro = "";
         String nimi = "";

         doc.getDocumentElement ().normalize ();
         NodeList vrLista = doc.getElementsByTagName("Vaalirengas");
         int maara = vrLista.getLength();
         for (int i = 0; i < maara; i++) {
             Node vrSolmu = vrLista.item(i);
             if(vrSolmu.getNodeType() == Node.ELEMENT_NODE){
                Element rengas = (Element) vrSolmu;
 
                NodeList nimiLista = rengas.getElementsByTagName("nimi");
                Element nimiElement = (Element)nimiLista.item(0);
                NodeList tekstiNimi = nimiElement.getChildNodes();
                nimi = (String)tekstiNimi.item(0).getNodeValue().trim();
              
 
               int rnro = Integer.parseInt(vrnro);
               
               malli.lisaaParsittuVaalirengas(nimi);
               vrnro = "";
               nimi = "";
     
  
            }
        }
      }
      catch(Exception e) {
         
          
      }
      
   }
      /*
       * Ei vielä implementoitu.
       * 
       */
   public void luoAanestyspaikka(String url) {
      try {
         DocumentBuilderFactory docBuilderF = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = docBuilderF.newDocumentBuilder();
         Document doc;
         doc = docBuilder.parse(url); 
          
      //   String tunnus = "";
         String nimi = "";
         String osoite = "";
        
         doc.getDocumentElement ().normalize ();
         NodeList apLista = doc.getElementsByTagName("Aanestyspaikka");
         int maara = apLista.getLength();
         for (int i = 0; i < maara; i++) {
             Node apSolmu = apLista.item(i);
             if(apSolmu.getNodeType() == Node.ELEMENT_NODE){
                Element ap = (Element)apSolmu;
  
              
                NodeList nimiLista = ap.getElementsByTagName("nimi");
                Element nimiElement = (Element)nimiLista.item(0);
                NodeList tekstiNimi = nimiElement.getChildNodes();
                nimi = (String)tekstiNimi.item(0).getNodeValue().trim();
              
                
                NodeList osoiteLista = ap.getElementsByTagName("Osoite");
               
                Element osoiteElement = (Element)osoiteLista.item(0);
                NodeList tekstiosoite = osoiteElement.getChildNodes();
                osoite = (String)tekstiosoite.item(0).getNodeValue().trim();
                
              

          //     int t = Integer.parseInt(tunnus);
               
             //  lisaaAanestyspaikka(t, nimi, osoite);
       //        tunnus = "";
               nimi = "";
               osoite = "";
               
  
            }
        }
      }
      catch(Exception e) {
         
          
      }
      
   }
 
}
