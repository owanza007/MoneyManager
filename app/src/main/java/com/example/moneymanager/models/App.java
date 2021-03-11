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
                cate = "Hóa đơn";
                break;
            case "Transportation":
                cate = "Vận chuyển";
                break;
            case "Home":
                cate = "Gia đình";
                break;
            case "Car":
                cate = "Xe hơi";
                break;
            case "Entertainment":
                cate = "Giải trí";
                break;
            case "Shopping":
                cate = "Mua sắm";
                break;
            case "Clothing":
                cate = "Ăn mặc";
                break;
            case "Insurance":
                cate = "Bảo hiểm";
                break;
            case "Tax":
                cate = "Thuế";
                break;
            case "Telephone":
                cate = "Cước thoại";
                break;
            case "Cigarette":
                cate = "Thuốc lá";
                break;
            case "Health":
                cate = "Sức khỏe";
                break;
            case "Sport":
                cate = "Thể thao";
                break;
            case "Baby":
                cate = "Trẻ nhỏ";
                break;
            case "Pet":
                cate = "Thú cưng";
                break;
            case "Beauty":
                cate = "Làm đẹp";
                break;
            case "Hamburger":
                cate = "Mua sắm";
                break;
            case "Vegetables":
                cate = "Rau củ";
                break;
            case "Snacks":
                cate = "Đồ ăn nhanh";
                break;
            case "Gift":
                cate = "Quà";
                break;
            case "Social":
                cate = "Quan hệ";
                break;
            case "Travel":
                cate = "Đi lại";
                break;
            case "Education":
                cate = "Giáo dục";
                break;
            case "Salary":
                cate = "Lương";
                break;
            case "Awards":
                cate = "Giải thưởng";
                break;
            case "Grants":
                cate = "Tài trợ";
                break;
            case "Sale":
                cate = "Bán hàng";
                break;
            case "Rental":
                cate = "Cho thuê";
                break;
            case "Refunds":
                cate = "Hoàn tiền";
                break;
            case "Coupons":
                cate = "Phiếu giảm giá";
                break;
            case "Lottery":
                cate = "Xổ số";
                break;
            case "Dividends":
                cate = "Cổ tức";
                break;
            case "Investments":
                cate = "Đầu tư";
                break;
            case "French fries":
                cate = "Khoai tây chiên";
                break;
            case "Noodle":
                cate = "Hủ tíu";
                break;
            case "Pizza":
                cate = "Bánh pizza";
                break;
            case "Fruit":
                cate = "Hoa quả";
                break;
            case "Ice-cream":
                cate = "Kem";
                break;
            case "Cake":
                cate = "Bánh kem";
                break;
            case "Tea":
                cate = "Trà";
                break;
            case "Wine":
                cate = "Rượu";
                break;
            case "Soda":
                cate = "Nước soda";
                break;
            case "Petrol":
                cate = "Xăng dầu";
                break;
            case "Gas station":
                cate = "Gas";
                break;
            case "Car wash":
                cate = "Rửa xe";
                break;

            case "Electric car":
                cate = "Sạc xe";
                break;

            case "Highway":
                cate = "Phí cao tốc";
                break;

            case "Truck":
                cate = "Vận tải";
                break;

            case "Bike":
                cate = "Xe đạp";
                break;

            case "Motorbike":
                cate = "Xe máy";
                break;

            case "Boat":
                cate = "Máy bay";
                break;

            case "Train":
                cate = "Tàu";
                break;

            case "Cart":
                cate = "Giỏ hàng";
                break;

            case "Dress":
                cate = "Váy";
                break;

            case "Underwear":
                cate = "Nội y";
                break;

            case "Man's shoes":
                cate = "Giày nam";
                break;

            case "Woman's shoes":
                cate = "Giày nữ";
                break;

            case "Glasses":
                cate = "Kính thời trang";
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
