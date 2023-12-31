package src.controller;

public class LoginSession {
    private static int id;
    private static String username;

    public void login(int id, String username) {
        this.id = id;
        this.username = username;

        System.out.println("User: " + getId() + " | " +  getUsername() + " Saved!");
    }

    public static void logout() {
        id = 0;
        username = null;
    }

    public void setId(int setId){
        id = setId;
    }
    public void setUsername(String setUser){
        username = setUser;
    }

    public static int getId(){
        return id;
    }
    public static String getUsername() {
        return username;
    }
}
