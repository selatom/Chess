package com.example.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private static final String TAG = "GameView";
    private Bitmap bmBoardW, bmBoardB, Bpawn, Bking, Bqueen, Bbishop, Brook, Bknight, Wpawn, Wking, Wqueen, Wbishop, Wrook, Wknight, winnerMessage;
    public static int sizeOfMap= 125*Constants.SCREEN_WIDTH/1080;

    private final int h=8;//משבצות בגובה
    private final int w =8;//משבצות ברוחב

    static ArrayList<Board> arrBoard =new ArrayList<>();
    private final  ArrayList<Board> arrCopyBoard =new ArrayList<>();
    static ArrayList<Soldires>arrWhiteSoldires=new ArrayList<>();
    static ArrayList<Soldires>arrBlackSoldires=new ArrayList<>();
    private ArrayList<Soldires>allSoldier=new ArrayList<>();

    private Soldires soldierPressed;
    private Board WIN;

    static boolean firstmove=true;
    private boolean isChess=false;
    private boolean endMach=false;
    private String colorNow="white";

    private Handler handler;
    private Runnable runnable;


    public GameView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        bmBoardB= BitmapFactory.decodeResource(this.getResources(), R.drawable.blackboard);
        bmBoardB=Bitmap.createScaledBitmap(bmBoardB, sizeOfMap, sizeOfMap, true);
        bmBoardW= BitmapFactory.decodeResource(this.getResources(), R.drawable.whiteboard);
        bmBoardW=Bitmap.createScaledBitmap(bmBoardW, sizeOfMap, sizeOfMap, true);
        winnerMessage=BitmapFactory.decodeResource(this.getResources(), R.drawable.win);
        winnerMessage=Bitmap.createScaledBitmap(winnerMessage, (5*sizeOfMap), (2*sizeOfMap), true);

        Bbishop= BitmapFactory.decodeResource(this.getResources(), R.drawable.bb);
        Bbishop=Bitmap.createScaledBitmap(Bbishop, sizeOfMap, sizeOfMap, true);

        Bpawn= BitmapFactory.decodeResource(this.getResources(), R.drawable.bp);
        Bpawn=Bitmap.createScaledBitmap(Bpawn, sizeOfMap, sizeOfMap, true);

        Bking= BitmapFactory.decodeResource(this.getResources(), R.drawable.bk);
        Bking=Bitmap.createScaledBitmap(Bking, sizeOfMap, sizeOfMap, true);

        Bqueen= BitmapFactory.decodeResource(this.getResources(), R.drawable.bq);
        Bqueen=Bitmap.createScaledBitmap(Bqueen, sizeOfMap, sizeOfMap, true);

        Brook= BitmapFactory.decodeResource(this.getResources(), R.drawable.br);
        Brook=Bitmap.createScaledBitmap(Brook, sizeOfMap, sizeOfMap, true);

        Bknight= BitmapFactory.decodeResource(this.getResources(), R.drawable.bn);
        Bknight=Bitmap.createScaledBitmap(Bknight, sizeOfMap, sizeOfMap, true);

        Wpawn= BitmapFactory.decodeResource(this.getResources(), R.drawable.wp);
        Wpawn=Bitmap.createScaledBitmap(Wpawn, sizeOfMap, sizeOfMap, true);

        Wking= BitmapFactory.decodeResource(this.getResources(), R.drawable.wk);
        Wking=Bitmap.createScaledBitmap(Wking, sizeOfMap, sizeOfMap, true);

        Wqueen= BitmapFactory.decodeResource(this.getResources(), R.drawable.wq);
        Wqueen=Bitmap.createScaledBitmap(Wqueen, sizeOfMap, sizeOfMap, true);

        Wbishop= BitmapFactory.decodeResource(this.getResources(), R.drawable.wb);
        Wbishop=Bitmap.createScaledBitmap(Wbishop, sizeOfMap, sizeOfMap, true);

        Wrook= BitmapFactory.decodeResource(this.getResources(), R.drawable.wr);
        Wrook=Bitmap.createScaledBitmap(Wrook, sizeOfMap, sizeOfMap, true);

        Wknight= BitmapFactory.decodeResource(this.getResources(), R.drawable.wn);
        Wknight=Bitmap.createScaledBitmap(Wknight, sizeOfMap, sizeOfMap, true);

        String name;

        //בונה את לוח המשבצות
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                name=(j+1)+""+(i+1);

                if((i+j)%2==0){//מוצא כל משבצת שנייה
                    arrBoard.add(new Board(bmBoardB, j*sizeOfMap+Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap,
                            i*sizeOfMap+170*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap, name, "black"));
                }else{
                    arrBoard.add(new Board(bmBoardW, j*sizeOfMap+Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap,
                            i*sizeOfMap+170*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap, name, "white"));
                }
            }
        }

        // רשימה ששומרת על הערכים המקוריים של הלוח
        for(int i=0; i<arrBoard.size(); i++){
            arrCopyBoard.add(new Board(arrBoard.get(i).getBm(), arrBoard.get(i).getX(), arrBoard.get(i).getY(), arrBoard.get(i).getWidth(), arrBoard.get(i).getHeight(), arrBoard.get(i).getName(), arrBoard.get(i).getSqColor()));
        }

        resetBoard();

        handler=new Handler();
        runnable= this::invalidate;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getResources().getColor(R.color.white));


        for(int i=0; i< arrBoard.size(); i++){
            canvas.drawBitmap(arrBoard.get(i).getBm(), arrBoard.get(i).getX(), arrBoard.get(i).getY(), null);
        }

        for(int i=0; i<arrBlackSoldires.size(); i++){
            arrBlackSoldires.get(i).draw(canvas);
        }
        for(int i=0; i<arrWhiteSoldires.size(); i++){
            arrWhiteSoldires.get(i).draw(canvas);
        }

        if(endMach){
            MainActivity.gameView.setAlpha(0.4f);
            canvas.drawBitmap(winnerMessage, (sizeOfMap*2), (sizeOfMap*5), null);
        }
    }

    // מאתחל את הלוח במצב ההתחלתי
    public void resetBoard(){
        endMach=false;
        // מאתחל את כל החיילים השחורים
        Rook rook=new Rook(Brook, arrBoard.get(0).getX(), arrBoard.get(0).getY(), "black", arrBoard.get(0));  arrBlackSoldires.add(rook);
        arrBoard.get(0).setColorOn("black");  arrBoard.get(0).setSoldires(rook);
        Knight knight=new Knight(Bknight, arrBoard.get(1).getX(), arrBoard.get(1).getY(), "black", arrBoard.get(1));  arrBlackSoldires.add(knight);
        arrBoard.get(1).setColorOn("black");  arrBoard.get(1).setSoldires(knight);
        Bishop bishop=new Bishop(Bbishop, arrBoard.get(2).getX(), arrBoard.get(2).getY(), "black", arrBoard.get(2));  arrBlackSoldires.add(bishop);
        arrBoard.get(2).setColorOn("black");  arrBoard.get(2).setSoldires(bishop);
        Queen queen=new Queen(Bqueen, arrBoard.get(3).getX(), arrBoard.get(3).getY(), "black", arrBoard.get(3));  arrBlackSoldires.add(queen);
        arrBoard.get(3).setColorOn("black");  arrBoard.get(3).setSoldires(queen);
        King king=new King(Bking, arrBoard.get(4).getX(), arrBoard.get(4).getY(), "black", arrBoard.get(4));  arrBlackSoldires.add(king);
        arrBoard.get(4).setColorOn("black");  arrBoard.get(4).setSoldires(king);
        bishop=new Bishop(Bbishop, arrBoard.get(5).getX(), arrBoard.get(5).getY(), "black", arrBoard.get(5));  arrBlackSoldires.add(bishop);
        arrBoard.get(5).setColorOn("black");  arrBoard.get(5).setSoldires(bishop);
        knight=new Knight(Bknight, arrBoard.get(6).getX(), arrBoard.get(6).getY(), "black", arrBoard.get(6));  arrBlackSoldires.add(knight);
        arrBoard.get(6).setColorOn("black");  arrBoard.get(6).setSoldires(knight);
        rook=new Rook(Brook, arrBoard.get(7).getX(), arrBoard.get(7).getY(), "black", arrBoard.get(7));  arrBlackSoldires.add(rook);
        arrBoard.get(7).setColorOn("black");  arrBoard.get(7).setSoldires(rook);
        Pawn pawn=new Pawn(Bpawn, arrBoard.get(8).getX(), arrBoard.get(8).getY(), "black", arrBoard.get(8));  arrBlackSoldires.add(pawn);
        arrBoard.get(8).setColorOn("black");  arrBoard.get(8).setSoldires(pawn);
        for(int i=9; i<16; i++){
            pawn=new Pawn(Bpawn, arrBoard.get(i).getX(), arrBoard.get(i).getY(), "black", arrBoard.get(i));  arrBlackSoldires.add(pawn);
            arrBoard.get(i).setColorOn("black");  arrBoard.get(i).setSoldires(pawn);
        }
        allSoldier.addAll(arrBlackSoldires);

        // מאתחל את כל החיילים הלבנים
        rook=new Rook(Wrook, arrBoard.get(56).getX(), arrBoard.get(56).getY(), "white", arrBoard.get(56));  arrWhiteSoldires.add(rook);
        arrBoard.get(56).setColorOn("white");  arrBoard.get(56).setSoldires(rook);
        knight=new Knight(Wknight, arrBoard.get(57).getX(), arrBoard.get(57).getY(), "white", arrBoard.get(57));  arrWhiteSoldires.add(knight);
        arrBoard.get(57).setColorOn("white");  arrBoard.get(57).setSoldires(knight);
        bishop=new Bishop(Wbishop, arrBoard.get(58).getX(), arrBoard.get(58).getY(), "white", arrBoard.get(58));  arrWhiteSoldires.add(bishop);
        arrBoard.get(58).setColorOn("white");  arrBoard.get(58).setSoldires(bishop);
        queen=new Queen(Wqueen, arrBoard.get(59).getX(), arrBoard.get(59).getY(), "white", arrBoard.get(59));  arrWhiteSoldires.add(queen);
        arrBoard.get(59).setColorOn("white");  arrBoard.get(59).setSoldires(queen);
        king=new King(Wking, arrBoard.get(60).getX(), arrBoard.get(60).getY(), "white", arrBoard.get(60));  arrWhiteSoldires.add(king);
        arrBoard.get(60).setColorOn("white");  arrBoard.get(60).setSoldires(king);
        bishop=new Bishop(Wbishop, arrBoard.get(61).getX(), arrBoard.get(61).getY(), "white", arrBoard.get(61));  arrWhiteSoldires.add(bishop);
        arrBoard.get(61).setColorOn("white");  arrBoard.get(61).setSoldires(bishop);
        knight=new Knight(Wknight, arrBoard.get(62).getX(), arrBoard.get(62).getY(), "white", arrBoard.get(62));  arrWhiteSoldires.add(knight);
        arrBoard.get(62).setColorOn("white");  arrBoard.get(62).setSoldires(knight);
        rook=new Rook(Wrook, arrBoard.get(63).getX(), arrBoard.get(63).getY(), "white", arrBoard.get(63));  arrWhiteSoldires.add(rook);
        arrBoard.get(63).setColorOn("white");  arrBoard.get(63).setSoldires(rook);
        pawn=new Pawn(Wpawn, arrBoard.get(48).getX(), arrBoard.get(48).getY(), "white", arrBoard.get(48));  arrWhiteSoldires.add(pawn);
        arrBoard.get(48).setColorOn("white");  arrBoard.get(48).setSoldires(pawn);
        for(int i=49; i<56; i++){
            pawn=new Pawn(Wpawn, arrBoard.get(i).getX(), arrBoard.get(i).getY(), "white", arrBoard.get(i));  arrWhiteSoldires.add(pawn);
            arrBoard.get(i).setColorOn("white");  arrBoard.get(i).setSoldires(pawn);
        }
        allSoldier.addAll(arrWhiteSoldires);
    }

    // מקבלת שם של משבצת ומחזירה את צבע החייל שעומד על המשבצת
    static String getcolorFromName(String name, ArrayList<Board>boards){
        for(int i=0; i<boards.size(); i++){
            if(boards.get(i).getName().equals(name)){ return boards.get(i).getColorOn(); }
        }
        return "";
    }

    // פעולה שמקבלת מערך של משבצות ומשתנה של אמת או שקר, אם המשתנה הוא אמת זה אומר שצריך לסמן את המקומות שהחייל יוכל ללכת אם לא אז צריך להחזיר את הלוח לקדמותו
    public void updateBoard(boolean isCorrect, ArrayList<Board>canGo){
        Bitmap whitemarked, blackmarked;

        whitemarked= BitmapFactory.decodeResource(this.getResources(), R.drawable.whitemarked);
        whitemarked=Bitmap.createScaledBitmap(whitemarked, sizeOfMap, sizeOfMap, true);
        blackmarked= BitmapFactory.decodeResource(this.getResources(), R.drawable.blackmarked);
        blackmarked=Bitmap.createScaledBitmap(blackmarked, sizeOfMap, sizeOfMap, true);

        if(isCorrect){
            for(int i=0; i<arrBoard.size(); i++)  {
                for(int k=0; k<canGo.size(); k++){
                    if(canGo.get(k).getName().equals(arrBoard.get(i).getName())) {
                        if(arrBoard.get(i).getSqColor().equals("black"))
                            arrBoard.get(i).setBm(blackmarked);
                        else
                            arrBoard.get(i).setBm(whitemarked);

                    } } } }

        this.invalidate();

    }

    public void move(Board square){
        isChess=false;

        for(int i=0; i<arrBoard.size(); i++){
            // משנה את ערכי המשבמת הישנה
            if(soldierPressed.getBoard()==arrBoard.get(i)){
                arrBoard.get(i).setColorOn("none");
                arrBoard.get(i).setSoldires(null);
            } }

        if(colorNow.equals("black") && firstmove){
            firstmove=false;
        }

        if(square.getSoldires()!=null){
            // השחקן אכל חייל של היריב, יש לקרוא לפעולת האכילה.
            eatOpponent(square.getSoldires());
        }

        for(int i=0; i<allSoldier.size(); i++){
            // מעדכן את פרטי החייל שזז
            if(allSoldier.get(i)==soldierPressed){
                allSoldier.get(i).setX(square.getX());
                allSoldier.get(i).setY(square.getY());
                allSoldier.get(i).setBoard(square);
            } }

        for(int i=0; i<arrBoard.size(); i++){
            // מעדכן משבצת חדשה
            if(square==arrBoard.get(i)){
                arrBoard.get(i).setColorOn(colorNow);
                arrBoard.get(i).setSoldires(soldierPressed);
            } }

        Soldires king=null;
        if(colorNow.equals("white")){
            for(int i=0; i<arrBlackSoldires.size(); i++){
                // מוצא את המלך השחור
                if(arrBlackSoldires.get(i).getName().equals("King")){
                    king=arrBlackSoldires.get(i);
                    break;

                } }

            colorNow="black";
        } else{
            for(int i=0; i<arrWhiteSoldires.size(); i++){
                // מוצא את המלך הלבן
                if(arrWhiteSoldires.get(i).getName().equals("King")){
                    king=arrWhiteSoldires.get(i);
                    break;

                } }

            colorNow="white";
        }

        assert king != null;
        if(king.isChess(arrBoard, king.getBoard())){
            if(king.isMat(king)){
                endMach=true;
            }
            isChess=true;
        }

        this.invalidate();
    }

    // הפעולה מקבלת חייל שהורגים אותו ומוציאה אותו מרשימת החיילים
    public void eatOpponent(Soldires soldire){
        if(colorNow.equals("white")){
            arrBlackSoldires.remove(soldire);
        }else{
            arrWhiteSoldires.remove(soldire);
        }

        allSoldier.remove(soldire);
    }

    // הפעולה מקבלת מערך של משבצות שאליהן יכול החייל להתקדם. היא בודקת את כל המהלכים ומוציאה מהרשימה את המהלכים שבהם ייגרם לשח כנגד מי ששיחק ומחזירה את הרשימה המעודכנת.
    public ArrayList<Board> checkNextMoveChess(ArrayList<Board>opption) {
        ArrayList<Board>collection=new ArrayList<>();
        ArrayList<Board>copyBoard=new ArrayList<>();
        Board board=null, kingBoard=null;
        Soldires king=null, oldSoldires=null;
        int indexSol=0, indexRemove=0;
        boolean eatSoldier;

        // מעתיק את לוח המשבצות
        for(int i=0; i<arrBoard.size(); i++){
            copyBoard.add(new Board(arrBoard.get(i)));
        }

        // מעדכן את המשבצת החדשה
        for(int i=0; i<opption.size(); i++){
            eatSoldier=false;

            // בודק האם האופציה היא לאכול שחקן של היריב
            if(opption.get(i).getSoldires()!=null){
                eatSoldier=true;
                oldSoldires=opption.get(i).getSoldires();

                // מתאים את מערך החיילים לפי הצבע שנשחק
                if(colorNow.equals("white")){
                    indexRemove=arrBlackSoldires.indexOf(opption.get(i).getSoldires());
                    arrBlackSoldires.remove(opption.get(i).getSoldires());
                }else{
                    indexRemove=arrWhiteSoldires.indexOf(opption.get(i).getSoldires());
                    arrWhiteSoldires.remove(opption.get(i).getSoldires());
                } }

            // מעדכן את המשבצת החדשה
            for(int j=0; j<copyBoard.size(); j++){
                if(opption.get(i).isEqual(copyBoard.get(j))){
                    copyBoard.get(j).setColorOn(colorNow);
                    copyBoard.get(j).setSoldires(soldierPressed);

                    break;
                } }

            // מעדכן את המשבצת הישנה
            for(int j=0; j<copyBoard.size(); j++){
                if(soldierPressed.getBoard().isEqual(copyBoard.get(j))){
                    copyBoard.get(j).setColorOn("none");
                    copyBoard.get(j).setSoldires(null);

                    break;
                } }

            // מעדכן את החייל
            for(int j=0; j<allSoldier.size(); j++){
                if(soldierPressed==allSoldier.get(j)){
                    indexSol=j;
                    board=allSoldier.get(j).getBoard();
                    allSoldier.get(j).setBoard(opption.get(i));
                } }

            if(colorNow.equals("black")){
                for(int j=0; j<arrBlackSoldires.size(); j++){
                    // מוצא את המלך השחור
                    if(arrBlackSoldires.get(j).getName().equals("King")){
                        king=arrBlackSoldires.get(j);
                        break;

                    } }


            } else{
                for(int j=0; j<arrWhiteSoldires.size(); j++){
                    // מוצא את המלך הלבן
                    if(arrWhiteSoldires.get(j).getName().equals("King")){
                        king=arrWhiteSoldires.get(j);
                        break;

                    } } }
            assert king != null;

            // מוצא את המשבצת של המלך
            for(int j=0; j<copyBoard.size(); j++){
                if(copyBoard.get(j).isEqual(king.getBoard())){
                    kingBoard=copyBoard.get(j);
                    break;
                } }

            if(king.isChess(copyBoard, kingBoard)){
                collection.add(opption.get(i));
            }

            // במידה והאופציה היא לאכול חייל של היריב התנאי יתממש ויחזיר את הפרטי החייל לקדמותו
            if(eatSoldier){
                if(colorNow.equals("white")){
                    arrBlackSoldires.add(indexRemove, oldSoldires);
                }else{
                    arrWhiteSoldires.add(indexRemove, oldSoldires);
                } }

            allSoldier.get(indexSol).setBoard(board);
        }

        opption.removeAll(collection);
        return opption;

    }




    //מטפלת במקרה של נגיעה בלוח
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ArrayList<Board>canGo= new ArrayList<>();
            ArrayList<Board>canRemove=new ArrayList<>();
            float x,y;

            x=event.getX();
            y=event.getY();

            for(int j=0; j<arrBoard.size(); j++){
                if(arrBoard.get(j).isMarked()){
                    // clear the board
                    canRemove.add(arrBoard.get(j));
                    arrBoard.get(j).setBm(arrCopyBoard.get(j).getBm());
                    arrBoard.get(j).setMarked(false);

                } }

            for(int i=0; i<arrBoard.size(); i++){
                if(x>=arrBoard.get(i).getX() && x<arrBoard.get(i).getX()+sizeOfMap && y>=arrBoard.get(i).getY() && y<arrBoard.get(i).getY()+sizeOfMap){
                    // מאתר את המשבצת שבה התרחשה הנגיעה

                    if(canRemove.contains(arrBoard.get(i))){
                        // אם הנגיעה גורמת לחייל לזוז
                        move(arrBoard.get(i));

                    }

                    // תנאי שמוודא שהחייל שנלחץ הוא מהצבע שתורו הגיע
                    if(arrBoard.get(i).getColorOn().equals(colorNow)){

                        soldierPressed=arrBoard.get(i).getSoldires();
                        //בדיקה שהחייל שמעוניין לזוז מהצבע שתורו
                        canGo=checkNextMoveChess(arrBoard.get(i).getSoldires().checkMove(arrBoard, arrBoard.get(i)));


                        for(int j=0; j<canGo.size(); j++){canGo.get(j).setMarked(true);}// מסמן בכל המשבצות האפשריות שהן ממורקרות

                        updateBoard(true, canGo);
                        break;
                    }else{updateBoard(false, canGo=new ArrayList<>()); }

                } } }


        return true;
    }
}
