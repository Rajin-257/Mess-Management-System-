import java.util.ArrayList;

public class Data {
    private String date;
    private String name;
    private String phn;
    private int rent;
    private double mealCount;
    private String list;
    private double cost;
    private int hu, mb, eb, cb, wb;
    double utiliti;

    static ArrayList<Data> pData = new ArrayList<>();
    static ArrayList<Data> bdata = new ArrayList<>();
    static ArrayList<Data> mData = new ArrayList<>();
    static ArrayList<Data> utility = new ArrayList<>();
    static ArrayList<TotalCost> tcost = new ArrayList<>();

    public Data(String name, String phn, int rent) {
        this.name = name;
        this.phn = phn;
        this.rent = rent;
    }

    public Data(String date, String name, String list, double cost) {
        this.date = date;
        this.name = name;
        this.list = list;
        this.cost = cost;
    }

    public Data(String date, String name, double mealCount) {
        this.date = date;
        this.name = name;
        this.mealCount = mealCount;
    }

    public Data(int hu, int mb, int eb, int cb, int wb) {
        this.hu = hu;
        this.mb = mb;
        this.eb = eb;
        this.cb = cb;
        this.wb = wb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getName() {
        return name;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMealCount() {
        return mealCount;
    }

    public void setMealCount(double mealCount) {
        this.mealCount = mealCount;
    }

    public int getHu() {
        return hu;
    }

    public void setHu(int hu) {
        this.hu = hu;
    }

    public int getMb() {
        return mb;
    }

    public void setMb(int mb) {
        this.mb = mb;
    }

    public int getEb() {
        return eb;
    }

    public void setEb(int eb) {
        this.eb = eb;
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public int getWb() {
        return wb;
    }

    public void setWb(int wb) {
        this.wb = wb;
    }

    static String[] getnames() {
        String[] names = new String[pData.size()];
        for (int i = 0; i < pData.size(); i++) {
            names[i] = pData.get(i).getName();
        }
        return names;
    }

    public static double totalMeal() {
        double totalmeal = 0;
        for (Data data : mData) {
            totalmeal += data.getMealCount();
        }
        return totalmeal;
    }

    public static double totalmealbyname(String name) {
        double totalmealbyname = 0;
        for (Data data : mData) {
            if (data.getName().equals(name)) {
                totalmealbyname += data.getMealCount();
            }
        }
        return totalmealbyname;
    }

    public static int totalBazarcost() {
        int bazarcost = 0;
        for (Data data : bdata) {
            bazarcost += data.cost;
        }
        return bazarcost;
    }

    public static double permeal() {
        Double permealcost = Data.totalBazarcost() / Data.totalMeal();
        return permealcost;
    }

    public static void CTotalCost() {
        for (Data data : pData) {
            String name = data.getName();
            double rentCost = 0;
            double utilityCost = 0;
            double totalmeal = 0;

            rentCost = data.getRent();

            for (Data utilityData : utility) {
                utilityCost += (utilityData.getHu() + utilityData.getMb() + utilityData.getEb() +
                        utilityData.getCb() + utilityData.getWb());
            }
            double utilityCostPerPerson = utilityCost / pData.size();

            for (Data mealData : mData) {
                if (mealData.getName().equals(name)) {
                    totalmeal += mealData.getMealCount();
                }
            }
            double totalMealCost = totalmeal * permeal();

            double totalCost = rentCost + utilityCostPerPerson + totalMealCost;

            TotalCost totalCostObject = new TotalCost(name, rentCost, utilityCostPerPerson,
            totalmeal, String.format("%.2f", permeal()), String.format("%.2f", totalMealCost),
            String.format("%.2f", totalCost));

            tcost.add(totalCostObject);
        }
    }

    public static class TotalCost {
        String name;
        double rentCost;
        double utilityCostPerPerson;
        double totalmeal;
        String permeal;
        String totalMealCost;
        String totalCost;
        
        public TotalCost(String name, double rentCost, double utilityCostPerPerson, double totalmeal,
                         String permeal, String totalMealCost, String totalCost) {
            this.name = name;
            this.rentCost = rentCost;
            this.utilityCostPerPerson = utilityCostPerPerson;
            this.totalmeal = totalmeal;
            this.permeal = permeal;
            this.totalMealCost = totalMealCost;
            this.totalCost = totalCost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getRentCost() {
            return rentCost;
        }

        public void setRentCost(double rentCost) {
            this.rentCost = rentCost;
        }

        public double getUtilityCostPerPerson() {
            return utilityCostPerPerson;
        }

        public void setUtilityCostPerPerson(double utilityCostPerPerson) {
            this.utilityCostPerPerson = utilityCostPerPerson;
        }

        public double getTotalmeal() {
            return totalmeal;
        }

        public void setTotalmeal(double totalmeal) {
            this.totalmeal = totalmeal;
        }

        public String getPermeal() {
            return permeal;
        }

        public void setPermeal(String permeal) {
            this.permeal = permeal;
        }

        public String getTotalMealCost() {
            return totalMealCost;
        }

        public void setTotalMealCost(String totalMealCost) {
            this.totalMealCost = totalMealCost;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
        }

        

        
    }
}
