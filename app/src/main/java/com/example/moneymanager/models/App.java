package com.example.moneymanager.models;

import android.util.Pair;

import com.example.moneymanager.R;

import java.util.HashMap;

public class App {
    private HashMap<String, Pair<Integer, Integer>>icons;

    private String arrType [] = {"food", "bills", "transportation", "home", "car", "entertainment", "shopping", "clothing", "insurance", "tax",
    "telephone", "cigarette", "health", "sport", "baby", "pet", "beauty", "hamburger", "vegetables", "snacks", "gift", "social", "travel", "education",
            "salary", "awards", "grants", "sale", "rental", "refunds", "coupons", "lottery", "dividends", "investments", "french fries", "noodle", "pizza",
            "bread", "fish", "fruit", "ice-cream", "cake", "tea", "wine", "soda", "petrol", "gas station", "car wash", "electric car", "highway", "truck",
            "bike", "motorbike", "plane", "boat", "train", "cart", "dress", "underwear", "man's shoes", "woman's shoes", "glasses"};

    public App(){
        icons = new HashMap<>();
        initialize();
    }
    public void initialize(){

        //-------------Expense-----------------//
        icons.put("food", new Pair<Integer, Integer>(R.mipmap.food, R.drawable.circle_image_pink));
        icons.put("bills", new Pair<Integer, Integer>(R.mipmap.bills, R.drawable.circle_image_blue_1));
        icons.put("transportation", new Pair<Integer, Integer>(R.mipmap.transportation, R.drawable.circle_image_green_2));
        icons.put("home", new Pair<Integer, Integer>(R.mipmap.home, R.drawable.circle_image_orange_1));
        icons.put("car", new Pair<Integer, Integer>(R.mipmap.car, R.drawable.circle_image_purple));
        icons.put("entertainment", new Pair<Integer, Integer>(R.mipmap.entertainment, R.drawable.circle_image_blue_2));
        icons.put("shopping", new Pair<Integer, Integer>(R.mipmap.shopping, R.drawable.circle_image_pink));
        icons.put("clothing", new Pair<Integer, Integer>(R.mipmap.clothing, R.drawable.circle_image_green_2));
        icons.put("insurance", new Pair<Integer, Integer>(R.mipmap.insurance, R.drawable.circle_image_purple));
        icons.put("tax", new Pair<Integer, Integer>(R.mipmap.tax, R.drawable.circle_image_green_2));
        icons.put("telephone", new Pair<Integer, Integer>(R.mipmap.telephone, R.drawable.circle_image_green_1));
        icons.put("cigarette", new Pair<Integer, Integer>(R.mipmap.cigar, R.drawable.circle_image_orange_2));
        icons.put("health", new Pair<Integer, Integer>(R.mipmap.health, R.drawable.circle_image_orange_2));
        icons.put("sport", new Pair<Integer, Integer>(R.mipmap.sport, R.drawable.circle_image_blue_1));
        icons.put("baby", new Pair<Integer, Integer>(R.mipmap.baby, R.drawable.circle_image_purple));
        icons.put("pet", new Pair<Integer, Integer>(R.mipmap.pet, R.drawable.circle_image_orange_2));
        icons.put("add", new Pair<Integer, Integer>(R.mipmap.add_item, R.drawable.circle_image_unselect));
        icons.put("beauty", new Pair<Integer, Integer>(R.mipmap.beauty, R.drawable.circle_image_pink));
        icons.put("hamburger", new Pair<Integer, Integer>(R.mipmap.hamburger, R.drawable.circle_image_orange_2));
        icons.put("vegetables", new Pair<Integer, Integer>(R.mipmap.vegetables, R.drawable.circle_image_green_1));
        icons.put("snacks", new Pair<Integer, Integer>(R.mipmap.snack, R.drawable.circle_image_pink));
        icons.put("gift", new Pair<Integer, Integer>(R.mipmap.gift, R.drawable.circle_image_orange_2));
        icons.put("social", new Pair<Integer, Integer>(R.mipmap.social, R.drawable.circle_image_purple));
        icons.put("travel", new Pair<Integer, Integer>(R.mipmap.travel, R.drawable.circle_image_green_2));
        icons.put("education", new Pair<Integer, Integer>(R.mipmap.education, R.drawable.circle_image_orange_1));

        //--------------Income-----------------//
        icons.put("salary", new Pair<Integer, Integer>(R.mipmap.salary, R.drawable.circle_image_orange_2));
        icons.put("awards", new Pair<Integer, Integer>(R.mipmap.awards, R.drawable.circle_image_orange_1));
        icons.put("grants", new Pair<Integer, Integer>(R.mipmap.grants, R.drawable.circle_image_green_1));
        icons.put("sale", new Pair<Integer, Integer>(R.mipmap.sale, R.drawable.circle_image_blue_2));
        icons.put("rental", new Pair<Integer, Integer>(R.mipmap.rental, R.drawable.circle_image_blue_1));
        icons.put("refunds", new Pair<Integer, Integer>(R.mipmap.refunds, R.drawable.circle_image_purple));
        icons.put("coupons", new Pair<Integer, Integer>(R.mipmap.coupons, R.drawable.circle_image_pink));
        icons.put("lottery", new Pair<Integer, Integer>(R.mipmap.lottery, R.drawable.circle_image_green_1));
        icons.put("dividends", new Pair<Integer, Integer>(R.mipmap.dividends, R.drawable.circle_image_green_2));
        icons.put("investments", new Pair<Integer, Integer>(R.mipmap.investments, R.drawable.circle_image_orange_1));


        //---------------Other-----------------//
        //---------------------Food------------//
        icons.put("french fries", new Pair<Integer, Integer>(R.mipmap.potato, R.drawable.circle_image_pink));
        icons.put("noodle", new Pair<Integer, Integer>(R.mipmap.noodle, R.drawable.circle_image_blue_1));
        icons.put("pizza", new Pair<Integer, Integer>(R.mipmap.pizza, R.drawable.circle_image_green_2));
        icons.put("bread", new Pair<Integer, Integer>(R.mipmap.bread, R.drawable.circle_image_orange_2));
        icons.put("fish", new Pair<Integer, Integer>(R.mipmap.fish, R.drawable.circle_image_blue_1));
        icons.put("fruit", new Pair<Integer, Integer>(R.mipmap.apple, R.drawable.circle_image_green_1));
        icons.put("ice-cream", new Pair<Integer, Integer>(R.mipmap.ice_cream, R.drawable.circle_image_purple));
        icons.put("cake", new Pair<Integer, Integer>(R.mipmap.cake, R.drawable.circle_image_orange_1));
        icons.put("tea", new Pair<Integer, Integer>(R.mipmap.tea, R.drawable.circle_image_blue_2));
        icons.put("wine", new Pair<Integer, Integer>(R.mipmap.glass, R.drawable.circle_image_purple));
        icons.put("soda", new Pair<Integer, Integer>(R.mipmap.soda, R.drawable.circle_image_blue_2));
        //----------------Transportation-------------//
        icons.put("petrol", new Pair<Integer, Integer>(R.mipmap.petrol, R.drawable.circle_image_orange_1));
        icons.put("gas station", new Pair<Integer, Integer>(R.mipmap.gas_station, R.drawable.circle_image_green_1));
        icons.put("car wash", new Pair<Integer, Integer>(R.mipmap.car_wash, R.drawable.circle_image_orange_2));
        icons.put("electric car", new Pair<Integer, Integer>(R.mipmap.electric_car, R.drawable.circle_image_blue_2));
        icons.put("highway", new Pair<Integer, Integer>(R.mipmap.highway, R.drawable.circle_image_green_1));
        icons.put("truck", new Pair<Integer, Integer>(R.mipmap.truck, R.drawable.circle_image_orange_1));
        icons.put("bike", new Pair<Integer, Integer>(R.mipmap.bike, R.drawable.circle_image_green_2));
        icons.put("motorbike", new Pair<Integer, Integer>(R.mipmap.motorbike, R.drawable.circle_image_pink));
        icons.put("plane", new Pair<Integer, Integer>(R.mipmap.plane, R.drawable.circle_image_blue_1));
        icons.put("boat", new Pair<Integer, Integer>(R.mipmap.boat, R.drawable.circle_image_purple));
        icons.put("train", new Pair<Integer, Integer>(R.mipmap.train, R.drawable.circle_image_green_2));
        //---------------Shopping---------------//
        icons.put("cart", new Pair<Integer, Integer>(R.mipmap.cart, R.drawable.circle_image_orange_1));
        icons.put("dress", new Pair<Integer, Integer>(R.mipmap.dress, R.drawable.circle_image_pink));
        icons.put("underwear", new Pair<Integer, Integer>(R.mipmap.underwear, R.drawable.circle_image_blue_2));
        icons.put("man's shoes", new Pair<Integer, Integer>(R.mipmap.shoes_man, R.drawable.circle_image_green_1));
        icons.put("woman's shoes", new Pair<Integer, Integer>(R.mipmap.shoes_woman, R.drawable.circle_image_pink));
        icons.put("glasses", new Pair<Integer, Integer>(R.mipmap.glasses, R.drawable.circle_image_blue_1));
    }
    public String convertVI(String category){
        String cate = "";
        switch (category){
            case "Food":
                cate = "food";
                break;
            case "Bills":
                cate = "H??a ????n";
                break;
            case "Transportation":
                cate = "V???n chuy???n";
                break;
            case "Home":
                cate = "Gia ????nh";
                break;
            case "Car":
                cate = "Xe h??i";
                break;
            case "Entertainment":
                cate = "Gi???i tr??";
                break;
            case "Shopping":
                cate = "Mua s???m";
                break;
            case "Clothing":
                cate = "??n m???c";
                break;
            case "Insurance":
                cate = "B???o hi???m";
                break;
            case "Tax":
                cate = "Thu???";
                break;
            case "Telephone":
                cate = "C?????c tho???i";
                break;
            case "Cigarette":
                cate = "Thu???c l??";
                break;
            case "Health":
                cate = "S???c kh???e";
                break;
            case "Sport":
                cate = "Th??? thao";
                break;
            case "Baby":
                cate = "Tr??? nh???";
                break;
            case "Pet":
                cate = "Th?? c??ng";
                break;
            case "Beauty":
                cate = "L??m ?????p";
                break;
            case "Hamburger":
                cate = "Mua s???m";
                break;
            case "Vegetables":
                cate = "Rau c???";
                break;
            case "Snacks":
                cate = "????? ??n nhanh";
                break;
            case "Gift":
                cate = "Qu??";
                break;
            case "Social":
                cate = "Quan h???";
                break;
            case "Travel":
                cate = "??i l???i";
                break;
            case "Education":
                cate = "Gi??o d???c";
                break;
            case "Salary":
                cate = "L????ng";
                break;
            case "Awards":
                cate = "Gi???i th?????ng";
                break;
            case "Grants":
                cate = "T??i tr???";
                break;
            case "Sale":
                cate = "B??n h??ng";
                break;
            case "Rental":
                cate = "Cho thu??";
                break;
            case "Refunds":
                cate = "Ho??n ti???n";
                break;
            case "Coupons":
                cate = "Phi???u gi???m gi??";
                break;
            case "Lottery":
                cate = "X??? s???";
                break;
            case "Dividends":
                cate = "C??? t???c";
                break;
            case "Investments":
                cate = "?????u t??";
                break;
            case "French fries":
                cate = "Khoai t??y chi??n";
                break;
            case "Noodle":
                cate = "H??? t??u";
                break;
            case "Pizza":
                cate = "B??nh pizza";
                break;
            case "Fruit":
                cate = "Hoa qu???";
                break;
            case "Ice-cream":
                cate = "Kem";
                break;
            case "Cake":
                cate = "B??nh kem";
                break;
            case "Tea":
                cate = "Tr??";
                break;
            case "Wine":
                cate = "R?????u";
                break;
            case "Soda":
                cate = "N?????c soda";
                break;
            case "Petrol":
                cate = "X??ng d???u";
                break;
            case "Gas station":
                cate = "Gas";
                break;
            case "Car wash":
                cate = "R???a xe";
                break;

            case "Electric car":
                cate = "S???c xe";
                break;

            case "Highway":
                cate = "Ph?? cao t???c";
                break;

            case "Truck":
                cate = "V???n t???i";
                break;

            case "Bike":
                cate = "Xe ?????p";
                break;

            case "Motorbike":
                cate = "Xe m??y";
                break;

            case "Boat":
                cate = "M??y bay";
                break;

            case "Train":
                cate = "T??u";
                break;

            case "Cart":
                cate = "Gi??? h??ng";
                break;

            case "Dress":
                cate = "V??y";
                break;

            case "Underwear":
                cate = "N???i y";
                break;

            case "Man's shoes":
                cate = "Gi??y nam";
                break;

            case "Woman's shoes":
                cate = "Gi??y n???";
                break;

            case "Glasses":
                cate = "K??nh th???i trang";
                break;
            default:
                cate = category;
                break;
        }
        return cate;
    }
    public Pair<Integer, Integer> getICons(String key){
        return icons.get(key);
    }


    public HashMap<String, Pair<Integer, Integer>> geticons() {
        return icons;
    }

    public void setIcons(HashMap<String, Pair<Integer, Integer>> icons) {
        this.icons = icons;
    }

    public String[] getArrType() {
        return arrType;
    }

    public void setArrType(String[] arrType) {
        this.arrType = arrType;
    }

}
