import java.util.Timer;
import java.util.TimerTask;

public class ItemDB {
    // MapのSPACEを入れるリストの作成
    // static List<Integer>[] listMapSpaceCoordinate=new ArrayList<>();
    public static int itemCount = 0;
    public static final int BANANA = 10;
    public static final int FISH = 11;
    public static final int SAKE = 12;
    public static final int BANANA_STOP = 1000;
    private static int spaceCount;
    public static int moveFISHGain = 1;
    public static int moveSakeGain = 1;
    private static int[][] listMapSpaceCoordinate = new int[150][2];

    public ItemDB() {
        this.spaceCount = 0;
        this.moveFISHGain = 1;
        this.moveSakeGain = 1;
        updateSpaceList();
    }

    //
    public static void updateSpaceList() {
        spaceCount = 0;
        for (int y = 0; y < MapData.getHeight(); y++) {
            for (int x = 0; x < MapData.getWidth(); x++) {
                if (MapData.getMap(x, y) == MapData.TYPE_SPACE) {
                    listMapSpaceCoordinate[spaceCount][0] = x;
                    listMapSpaceCoordinate[spaceCount][1] = y;
                    spaceCount++;
                }
            }
        }
        spaceCount--;
    }

    // randomに設置可能な座標を返す
    public static int[] getItemCoordinate() {
        updateSpaceList();
        int[] coordinate = new int[2];
        int randomNum = (int) (Math.random() * spaceCount);
        coordinate[0] = listMapSpaceCoordinate[randomNum][0];
        coordinate[1] = listMapSpaceCoordinate[randomNum][1];
        return coordinate;
    }

    public static void setItem(int Item) {
        int[] setCoordinate = getItemCoordinate();
        System.out.println(setCoordinate[0] + "," + setCoordinate[1]);
        MapData.setMap(setCoordinate[0], setCoordinate[1], Item);
    }

    // BANANA_STOP秒間停止する
    static void actionBANANA() {
        try {
            moveFISHGain = 0;
            Thread.sleep(BANANA_STOP);
            moveFISHGain = 1;
        } catch (InterruptedException e) {
            // 例外処理
            System.out.println("Error: actionBANANA");
        }
    }

    // 移動量が2倍になる
    public static void actionFISH() {
        moveFISHGain = 2;
    }

    // 入力が逆になる
    public static void actionSAKE() {
        try {
            moveSakeGain = -1;
            Thread.sleep(BANANA_STOP);
            moveSakeGain = 1;
        } catch (InterruptedException e) {
            // 例外処理
            System.out.println("Error: actionBANANA");
        }
    }

    public static void startAction(int Item) {
        System.out.println("Item: " + Item);
        switch (Item) {
            case 0:
                // SPACE
                break;
            case 1:
                // Wall
                break;
            case 10:
                // BANANA
                addItemCount(1);
                System.out.println("itemCount:" + itemCount);
                actionBANANA();
                break;
            case 11:
                // FISH
                addItemCount(1);
                System.out.println("itemCount:" + itemCount);
                actionFISH();
                break;
            case 12:
                // SAKE
                addItemCount(1);
                System.out.println("itemCount:" + itemCount);
                actionSAKE();
                break;

            default:
                System.out.println("Error: startAction");
                break;
        }
    }

    static void addItemCount(int gain) {
        itemCount += gain;
    }

    static int getItemCount() {
        return itemCount;
    }

    static boolean IsGetAllItems() {

        int mustGetNoOfItem = 3;

        if (getItemCount() >= mustGetNoOfItem) {
            return true;
        } else {
            return false;
        }
    }
}
