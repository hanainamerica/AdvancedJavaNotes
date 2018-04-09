package scale;

/**
 * Created by rr on 11/4/17.
 */


public class EditThread extends Thread{
    private int threadno;
    private EditOptions editOptions;
    private String[] info;


    public EditThread(int threadno, EditOptions editOptions, String[] info) {
        this.editOptions = editOptions;
        this.threadno = threadno;
        this.info = info;
    }

    @Override
    public void run() {
        switch (threadno) {
            case 0:
                System.out.println("Start thread " + threadno + " updateOptionPrice");
                break;
            case 1:
                System.out.println("Start thread " + threadno + " updateOptionSetName");
                break;
        }
        operations();
        System.out.println("Stopping thread " + threadno);
    }

    public void operations() {
        switch (threadno) {
            case 0:
                editOptions.updateOptionPrice(info);
                break;
            case 1:
                editOptions.updateOptionSetName(info);
                break;
        }
    }

}