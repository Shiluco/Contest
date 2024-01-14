import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_OTHERS = 2;
    private static final String mapImageFiles[] = {
            "png/SPACE.png", // 0
            "png/WALL-1.png", // 1
            "png/WALL-2.png", // 2
            "png/WALL-3.png", // 3
            "png/WALL-4.png", // 4
            "png/WALL-5.png", // 5
            "png/WALL-6.png", // 6
            "png/WALL-7.png", // 7
            "png/WALL-8.png", // 8
            "png/WALL-9.png", // 9
            "png/WALL-10.png", // 10
            "png/WALL-11.png", // 11
            "png/WALL.png"// 12
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width; // width of the map
    private int height; // height of the map

    MapData(int x, int y) {
        mapImages = new Image[13];
        mapImageViews = new ImageView[y][x];
        for (int i = 0; i < 13; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }
        width = x;
        height = y;
        maps = new int[y][x];
        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        setImageViews();
    }

    // fill two-dimensional arrays with a given number (maps[y][x])
    private void fillMap(int type) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = type;
            }
        }
    }

    // dig walls for making roads
    private void digMap(int x, int y) {
        setMap(x, y, MapData.TYPE_SPACE);
        int[][] dl = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
        int[] tmp;

        for (int i = 0; i < dl.length; i++) {
            int r = (int) (Math.random() * dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i = 0; i < dl.length; i++) {
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x + dx * 2, y + dy * 2) == MapData.TYPE_WALL) {
                setMap(x + dx, y + dy, MapData.TYPE_SPACE);
                digMap(x + dx * 2, y + dy * 2);
            }
        }
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public void setMap(int x, int y, int type) {
        if (x < 1 || width <= x - 1 || y < 1 || height <= y - 1) {
            return;
        }
        maps[y][x] = type;
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    // public void setImageViews() {
    // for (int y = 0; y < height; y++) {
    // for (int x = 0; x < width; x++) {
    // mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
    // }
    // }
    // }

    public void setImageViews() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[getDetailImage(x, y)]);

            }
        }
    }

    public int getDetailImage(int x, int y) {
        int top = 0;
        int under = 0;
        int right = 0;
        int left = 0;
        if ((x - 1) >= 0) {
            top = maps[y][x - 1];
        }
        if ((x + 1) < width) {
            under = maps[y][x + 1];
        }
        if ((y - 1) >= 0) {
            left = maps[y - 1][x];
        }
        if ((y + 1) < height) {
            right = maps[y + 1][x];
        }

        int enter = maps[y][x];
        // 四隅の処理
        if (x == 0 && y == 0) {
            return 5;
        }
        if (x == 0 && y == (height - 1)) {
            return 3;
        }
        if (x == (width - 1) && y == 0) {
            return 6;
        }
        if (x == (width - 1) && y == (height - 1)) {
            return 4;
        }

        // // 一番左の列
        // if (y == 0) {
        // if (right == 1) {
        // return 8;
        // } else {
        // return 1;
        // }
        // }
        // // 一番右の列
        // if (y == (height - 1)) {
        // if (left == 1) {
        // return 10;
        // } else {
        // return 1;
        // }
        // }
        // // 一番上の行
        // if (x == 0) {
        // if (under == 1) {
        // return 9;
        // } else {
        // return 2;
        // }
        // }
        // // 一番下の行
        // if (x == (width - 1)) {
        // if (top == 1) {
        // return 7;
        // } else {
        // return 2;
        // }
        // }

        // SPACE
        if (enter == 0) {
            return 0;
        }

        // 縦一 (y+1)(y-1)=1 & (x+1)+(x-1)=0
        else if ((under * top) == 1 && (right + left) == 0) {
            return 2;
        }
        // 横一 (x+1)(x-1)=1 & (y+1)+(y-1)=0
        else if ((right * left) == 1 && (under + top) == 0) {
            return 1;
        }

        // 上-右 (y-1)(x+1)=1 & (x-1)+(y+1)=0
        else if ((top * right) == 1 && (left + under) == 0) {
            return 6;
        }
        // 上-左 (y-1)(x-1)=1 & (x+1)+(y+1)=0
        else if ((top * left) == 1 && (right + under) == 0) {
            return 4;
        }
        // 下-右 (y+1)(x+1)=1 & (x-1)+(y-1)=0
        else if ((under * right) == 1 && (left + top) == 0) {
            return 5;
        }
        // 下-左 (y+1)(x-1)=1 & (x+1)+(y-1)=0
        else if ((under * left) == 1 && (right + top) == 0) {
            return 3;
        }

        // 下以外 (y-1)(x+1)(x-1)=1 & (y+1)=0
        else if ((top * right * left) == 1 && under == 0) {
            return 10;
        }
        // 左以外 (y-1)(y+1)(x+1)=1 & (x-1)=0
        else if ((top * under * right) == 1 && left == 0) {
            return 9;
        }
        // 上以外 (y+1)(x+1)(x-1)=1 & (y-1)=0
        else if ((under * right * left) == 1 && top == 0) {
            return 8;
        }
        // 右以外 (y-1)(y+1)(x-1)=1 & (x+1)=0
        else if ((top * under * left) == 1 && right == 0) {
            return 7;
        }
        // 十字 (x+1)(x-1)(y+1)(y-1)=1
        else if ((top * under * left * right) == 1) {
            return 11;
        }

        // 上右左以外 下右左以外
        else if ((top + right + left) == 0 || (under + right + left) == 0) {
            return 2;
        }
        return 1;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}