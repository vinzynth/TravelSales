package at.tug.oad.travelsales;

import at.tug.oad.travelsales.controller.TravelSalesViewController;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 14:31:15
 * 
 */
public class TravelSales {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            
            /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        //</editor-fold>
            
		TravelSalesViewController.getInstance().start();
		System.out.println("[Startup] Started");
	}

}
