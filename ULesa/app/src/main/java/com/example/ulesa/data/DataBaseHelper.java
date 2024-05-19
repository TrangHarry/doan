package com.example.ulesa.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ulesa.model.CommentModel;
import com.example.ulesa.model.Date;
import com.example.ulesa.model.HomeModel;
import com.example.ulesa.model.OrderModel;
import com.example.ulesa.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class DataBaseHelper {
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trangdata-1e653-default-rtdb.firebaseio.com/");
    private static final String TAG = "TRANG_DataBaseHelper";
    private static ArrayList<HomeModel> listHome;
    private static ArrayList<Date> listDate;
    private static ArrayList<UserModel> listUser;
    private static ArrayList<OrderModel> listOder;
    public static ArrayList<Date> listDateBooked;
    public static ArrayList<CommentModel> listComment;
    private static HomeModel dataDetail;
    private static UserModel dataUser;
    private static OrderModel dataOrder;
    private  static Date dataDate;

    public DataBaseHelper() {
        listHome = new ArrayList<>();
        listUser = new ArrayList<>();
        listOder = new ArrayList<>();
        listDate = new ArrayList<>();
    }

    public static ArrayList<Date> getDateBooked(String idRoom, Context context) {
        Log.d(TAG, "getDateBooked() " + idRoom);
        listDateBooked = new ArrayList<>();
        db.child("Booked").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.child(idRoom).getChildren()) {
                    String data =item.getValue(String.class);
                    String[] dataDate = data.split("/");
                    Date date = new Date(Integer.parseInt(dataDate[0]), Integer.parseInt(dataDate[1]), Integer.parseInt(dataDate[2]));
                    Log.d(TAG, "getDateBooked " + date.toString());
                    listDateBooked.add(date);
                }
                Intent intent = new Intent("com.example.appulesa.DATA_DONE");
                context.sendBroadcast(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listDateBooked;
    }
    public  ArrayList<Date> getListDate(){
        return listDateBooked;
    }
    public static void addBooked(String idRoom, Date date, Context context) {
        Log.d(TAG, "addBooked() " + idRoom);

        db.child("Booked").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = 1; i <= 1000; i++) {
                    if (!snapshot.child(idRoom).hasChild(String.valueOf(i))) {
                        Log.d(TAG, "addBooked() " + i);
                        db.child("Booked").child(idRoom).child(String.valueOf(i)).setValue(date.toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public ArrayList<UserModel> getDataUser(Context context) {
        Log.d(TAG, "getDataUser()");
        ArrayList<UserModel> list = new ArrayList<>();
        db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                for (DataSnapshot item : snapshot.getChildren()) {
                    Log.d(TAG, "onDataChange " + item.getKey());
                    UserModel userModel = new UserModel(item.getKey(), item);
                    Log.d(TAG, "onDataChange " + userModel.toString());
                    list.add(userModel);
                }
                Log.d(TAG, "TRANG" + list.size());
                sendDataGetDone(context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        Log.d(TAG, "TRANG" + list.size());
        listUser = list;
        return list;
    }

    public static ArrayList<CommentModel> getListComment(Context context, int id, String idHome) {
        if (listComment != null) return listComment;
        Log.d(TAG, "TTRANG_getListComment");
        ArrayList<CommentModel> list = new ArrayList<>();
        db.child("Cmt").child(idHome).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    CommentModel commentModel = new CommentModel(item.getKey(), item);
                    Log.d(TAG, "TTRANG_ " + item.getKey());
                    db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d(TAG, "onDataChange " + snapshot.getValue());
                            DataSnapshot data = snapshot.child(commentModel.getUserID());
                            UserModel userModel = new UserModel(data.getKey(), data);
                            Log.d(TAG, "TTRANG_ " + String.valueOf(userModel));
                            Log.d(TAG, String.valueOf(userModel));
                            commentModel.setUserModel(userModel);
                            list.add(commentModel);
                            listComment = list;
                            sendDataGetDone(context, id);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled ");
                        }
                    });
                }
                listComment = list;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    public static void addComment(Context context, CommentModel commentModel, String idroom, int stt) {
        Log.d(TAG, "addComment id room " + idroom);
        db.child("Cmt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Random random = new Random();
                int id = random.nextInt(1000);
                while (snapshot.hasChild("Cmt" + id)) {
                    Log.d(TAG, "addComment while " + id);
                    id = random.nextInt(1000);
                }
                String newID = "Cmt" + id;
                Log.d(TAG, "addComment id" + newID);
                db.child("Cmt").child(idroom).child(newID).child("Evaluate").setValue(commentModel.getEvaluate());
                db.child("Cmt").child(idroom).child(newID).child("IDUser").setValue(commentModel.getUserID());
                db.child("Cmt").child(idroom).child(newID).child("Value").setValue(commentModel.getCmt());
                sendDataGetDone(context, stt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public UserModel getDataUser(Context context, String id) {
        Log.d(TAG, "getDataUser()");
        db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                DataSnapshot data = snapshot.child(id);
                UserModel userModel = new UserModel(data.getKey(), data);
                Log.d(TAG, String.valueOf(userModel));
                dataUser = userModel;
                Intent intent = new Intent("com.example.appulesa.DATA_DONE");
                intent.putExtra("order_user", dataUser);
                Log.d(TAG,"------------" + String.valueOf(dataUser));
                context.sendBroadcast(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        return dataUser;
    }

    public ArrayList<OrderModel> getDataOrder(Context context) {

        Log.d(TAG, "getdataorder()");
        ArrayList<OrderModel> list = new ArrayList<>();
        db.child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                String idUser = getID(context);
                Log.d(TAG, "onDataChange " + idUser);
                for (DataSnapshot item : snapshot.getChildren()) {
                    Log.d(TAG, "onDataChange " + item.getKey());
                    OrderModel orderModel = new OrderModel(item.getKey(), item);
                    Log.d(TAG, "onDataChange id" + orderModel.getIdUser());
                    if (orderModel.getIdUser().equals(idUser)) {
                        list.add(orderModel);
                        db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d(TAG, "onDataChange " + snapshot.getValue());
                                DataSnapshot data = snapshot.child(orderModel.getNameID());
                                HomeModel homeModel = new HomeModel(data.getKey(), data);
                                Log.d(TAG, String.valueOf(homeModel));
                                orderModel.setPictureMain(homeModel.getPictureMain());
                                orderModel.setRoomName(homeModel.getRoomName());
                                sendDataGetDone(context);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d(TAG, "onDataChange ");
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        Log.d(TAG, "TRANG" + list.size());
        listOder = list;
        return list;
    }

    public ArrayList<OrderModel> getDataOrderMan(Context context) {

        Log.d(TAG, "getdataorder()");
        ArrayList<OrderModel> list = new ArrayList<>();
        db.child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                String idUser = getID(context);
                Log.d(TAG, "onDataChange " + idUser);
                for (DataSnapshot item : snapshot.getChildren()) {
                    Log.d(TAG, "onDataChange " + item.getKey());
                    OrderModel orderModel = new OrderModel(item.getKey(), item);
                    Log.d(TAG, "onDataChange id" + orderModel.getIdUser());
                    list.add(orderModel);
                    db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d(TAG, "onDataChange " + snapshot.getValue());
                            DataSnapshot data = snapshot.child(orderModel.getNameID());
                            HomeModel homeModel = new HomeModel(data.getKey(), data);
                            Log.d(TAG, String.valueOf(homeModel));
                            orderModel.setPictureMain(homeModel.getPictureMain());
                            orderModel.setRoomName(homeModel.getRoomName());
                            Log.d(TAG, "sendDataGetDone acasfasd");
                            sendDataGetDone(context);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onDataChange ");
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        Log.d(TAG, "TRANG" + list.size());
        listOder = list;
        return list;
    }

    public static void deleteOrder(Context context, String id, int STT) {
        db.child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.child(id).getRef().removeValue();
                sendDataGetDone(context, STT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void deleteUser(Context context, String id, int STT) {
        Log.d(TAG,"deleteUser " + id);
        db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.child(id).getRef().removeValue();
                sendDataGetDone(context, STT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void deleteRoom(Context context, String id, int STT) {
        Log.d(TAG,"deleteRoomr " + id);
        db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.child(id).getRef().removeValue();
                sendDataGetDone(context, STT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static ArrayList<CommentModel> getListCommetnXX() {
        if (listComment == null) return new ArrayList<CommentModel>();
        else return listComment;
    }

    public OrderModel getDataOrderr(Context context, String id, int stt) {
        Log.d(TAG, "getDataOrderr() " + id);
        db.child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                DataSnapshot data = snapshot.child(id);
                OrderModel orderModel = new OrderModel(data.getKey(), data);
                db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange " + snapshot.getValue());
                        DataSnapshot data = snapshot.child(orderModel.getNameID());
                        HomeModel homeModel = new HomeModel(data.getKey(), data);
                        Log.d(TAG, String.valueOf(homeModel));
                        orderModel.setPictureMain(homeModel.getPictureMain());
                        orderModel.setRoomName(homeModel.getRoomName());
                        Intent intent = new Intent("com.example.appulesa.DATA_DONE" + stt);
                        intent.putExtra("order_model", orderModel);
                        Log.d(TAG,"------------" + String.valueOf(orderModel));
                        context.sendBroadcast(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onDataChange ");
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        return dataOrder;
    }

    public ArrayList<HomeModel> getDataHome(Context context) {
        Log.d(TAG, "getDataHome()");
        ArrayList<HomeModel> list = new ArrayList<>();
        db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                for (DataSnapshot item : snapshot.getChildren()) {
                    Log.d(TAG, "getDataHome onDataChange " + item.getKey());
                    HomeModel homeModel = new HomeModel(item.getKey(), item);
                    Log.d(TAG, "getDataHome onDataChange " + homeModel.toString());
                    list.add(homeModel);
                }
                Log.d(TAG, "TRANG size" + list.size());
                sendDataGetDone(context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        Log.d(TAG, "TRANG" + list.size());
        listHome = list;
        return list;
    }

    public HomeModel getDataDetail(Context context, String id, int stt) {
        Log.d(TAG, "getDataDetail() " + dataDetail);
        db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange " + snapshot.getValue());
                DataSnapshot data = snapshot.child(id);
                HomeModel homeModel = new HomeModel(data.getKey(), data);
                Log.d(TAG, String.valueOf(homeModel));
                dataDetail = homeModel;
                Intent intent = new Intent("com.example.appulesa.DATA_DONE" + stt);
                intent.putExtra("detail_room", homeModel);
                context.sendBroadcast(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onDataChange ");
            }
        });
        return dataDetail;
    }

    public static void addBill(Context context, int cus, Date date, String idRoom, String idUser,
                        int KM, int status, String timeIn, String timeOut,
                        int total,
                        int WC) {
        Log.d(TAG, "addBill");

        db.child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Random random = new Random();
                int id = random.nextInt(1000);
                while (snapshot.hasChild(String.valueOf(id))) {
                    Log.d(TAG, "addBill while " + id);
                    id = random.nextInt(1000);
                }
                Log.d(TAG, "addBill id" + id);
                db.child("Bill").child(String.valueOf(id)).child("Cus").setValue(cus);
                db.child("Bill").child(String.valueOf(id)).child("Date").setValue(date.toString());
                db.child("Bill").child(String.valueOf(id)).child("IDRoom").setValue(idRoom);
                db.child("Bill").child(String.valueOf(id)).child("IDUser").setValue(idUser);
                db.child("Bill").child(String.valueOf(id)).child("KM").setValue(KM);
                db.child("Bill").child(String.valueOf(id)).child("Status").setValue(status);
                db.child("Bill").child(String.valueOf(id)).child("TimeIn").setValue(timeIn);
                db.child("Bill").child(String.valueOf(id)).child("TimeOut").setValue(timeOut);
                db.child("Bill").child(String.valueOf(id)).child("Total").setValue(total);
                db.child("Bill").child(String.valueOf(id)).child("WC").setValue(WC);
                sendDataGetDone(context, 3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void addDate(Context context, Date date) {
        Log.d(TAG, "addDate");

        db.child("Booked").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Random random = new Random();
                String IDRoom = getID(context);
                int id = random.nextInt(1000);
                while (snapshot.hasChild(String.valueOf(id))) {
                    Log.d(TAG, "addDate while " + id);
                    id = random.nextInt(1000);
                }
                Log.d(TAG, "addDate id" + id);
                db.child("Booked").child("IDRoom").child(String.valueOf(id)).setValue(date.toString());
                sendDataGetDone(context, 3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static void changeIn4(String newname, String newgmail, String newdate,
                                 String newphone, String newgender, Context context) {
        Log.d(TAG, "changeIn4()");

        String id = getID(context);
        db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                db.child("loginCus").child(id).child("Name").setValue(newname);
                db.child("loginCus").child(id).child("Gmail").setValue(newgmail);
                db.child("loginCus").child(id).child("BirthDay").setValue(newdate);
                db.child("loginCus").child(id).child("Phone").setValue(newphone);
                db.child("loginCus").child(id).child("Gender").setValue(newgender);
                sendDataGetDone(context, 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void changeStatus(String idBill,int newstatus, Context context) {
        Log.d(TAG, "changeStatus() " + idBill);

        String id = getID(context);
        db.child("Bill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                db.child("Bill").child(idBill).child("Status").setValue(newstatus);
                sendDataGetDone(context, 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void changeRoom(String idRoom, HomeModel homeModel, Context context, int stt) {
        Log.d(TAG, "changeRoom() TRANG" + homeModel.getPrice());

        String id = getID(context);
        db.child("main").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                db.child("main").child(idRoom).child("RoomName").setValue(homeModel.getRoomName());
                db.child("main").child(idRoom).child("Review").setValue(homeModel.getReview());
                db.child("main").child(idRoom).child("Price").setValue(homeModel.getPrice());
                db.child("main").child(idRoom).child("Evaluate").setValue(homeModel.getEvaluate());
                db.child("main").child(idRoom).child("ViewEvaluate").setValue(homeModel.getViewEvaluate());
                db.child("main").child(idRoom).child("Location").setValue(homeModel.getLocation());
                db.child("main").child(idRoom).child("PictureMain").setValue(homeModel.getPictureMain());
                db.child("main").child(idRoom).child("ViewBooking").setValue(homeModel.getViewBooking());
                db.child("main").child(idRoom).child("Cus").setValue(homeModel.getCus());
                sendDataGetDone(context, stt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void changePassWord(String oldPass, String newPass, Context context) {
        Log.d(TAG, "changePassWord()");

        String id = getID(context);
        String email = getUserName((context));
        db.child("loginCus").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TRANG_changePassWord", " changePassWord");
                String pass = snapshot.child(id).child("PassWord").getValue(String.class);
                Log.d("TRANG_changePassWord", " changePassWord " + pass);
                if (pass.equals(oldPass)) {
                    Log.d("TRANG_changePassWord", " Mk cu dung");
                    db.child("loginCus").child(id).child("PassWord").setValue(newPass);
                    sendDataGetDone(context);
                    return;
                }
                sendDataGetFalse(context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.child("loginMan").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TRANG_changePassWord", " changePassWord");
                String pass = snapshot.child(id).child("PassWord").getValue(String.class);
                Log.d("TRANG_changePassWord", " changePassWord " + pass);
                if (pass.equals(oldPass)) {
                    Log.d("TRANG_changePassWord", " Mk cu dung");
                    db.child("loginMan").child(id).child("PassWord").setValue(newPass);
                    sendDataGetDone(context);
                    return;
                }
                sendDataGetFalse(context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static void sendDataGetDone(Context context) {
        Intent intent = new Intent("com.example.appulesa.DATA_DONE");
        context.sendBroadcast(intent);
    }

    private static void sendDataGetDone(Context context, int stt) {
        Intent intent = new Intent("com.example.appulesa.DATA_DONE" + stt);
        context.sendBroadcast(intent);
    }

    private static void sendDataGetFalse(Context context) {
        Intent intent = new Intent("com.example.appulesa.DATA_FALSE");
        context.sendBroadcast(intent);
    }

    //    public static ArrayList<DetailModel> getListDetail(){
//        return listDetail;
//    }
    public static ArrayList<UserModel> getListHome() {
        return listUser;
    }

    public UserModel getDataUser() {
        return dataUser;
    }

    public static ArrayList<OrderModel> getListOder() {
        return listOder;
    }

    public OrderModel getDataOrder() {
        return dataOrder;
    }

    public static void saveTK(String user, String pass, String name, Context context, String userID) {
        Log.d(TAG, "saveTK " + name);
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEUSER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user);
        editor.putString("password", pass);
        editor.putString("name", name);
        editor.putString("userid", userID);
        editor.apply();
    }

    public static void xoasTK(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEUSER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.remove("name");
        editor.remove("userid");
        editor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEUSER", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null);
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEUSER", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    public static String getID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEUSER", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userid", null);
    }

    public static String getPassWord(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEUSER", Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", null);
    }

    public static String getIDRoom(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SAVEIDRoom", Context.MODE_PRIVATE);
        return sharedPreferences.getString("idroom", null);
    }
}
