import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Data {
    private int id;
    private String date;
    private String name;
    private String phn;
    private int rent;
    private double mealCount;
    private String list;
    private double cost;
    private int hu, mb, eb, cb, wb;

    // Person constructor
    public Data(String name, String phn, int rent) {
        this.name = name;
        this.phn = phn;
        this.rent = rent;
    }
    
    // Person constructor with ID
    public Data(int id, String name, String phn, int rent) {
        this.id = id;
        this.name = name;
        this.phn = phn;
        this.rent = rent;
    }

    // Bazar constructor
    public Data(String date, String name, String list, double cost) {
        this.date = date;
        this.name = name;
        this.list = list;
        this.cost = cost;
    }
    
    // Bazar constructor with ID
    public Data(int id, String date, String name, String list, double cost) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.list = list;
        this.cost = cost;
    }

    // Meal constructor
    public Data(String date, String name, double mealCount) {
        this.date = date;
        this.name = name;
        this.mealCount = mealCount;
    }
    
    // Meal constructor with ID
    public Data(int id, String date, String name, double mealCount) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.mealCount = mealCount;
    }

    // Utility constructor
    public Data(int hu, int mb, int eb, int cb, int wb) {
        this.hu = hu;
        this.mb = mb;
        this.eb = eb;
        this.cb = cb;
        this.wb = wb;
    }
    
    // Utility constructor with ID and date
    public Data(int id, String date, int hu, int mb, int eb, int cb, int wb) {
        this.id = id;
        this.date = date;
        this.hu = hu;
        this.mb = mb;
        this.eb = eb;
        this.cb = cb;
        this.wb = wb;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
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

    // Database Operations for Person
    public static boolean addPerson(Data person) {
        String sql = "INSERT INTO person (name, phone, rent) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getPhn());
            pstmt.setInt(3, person.getRent());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding person: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean updatePerson(Data person) {
        String sql = "UPDATE person SET name = ?, phone = ?, rent = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getPhn());
            pstmt.setInt(3, person.getRent());
            pstmt.setInt(4, person.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating person: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean deletePerson(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting person: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static ArrayList<Data> getAllPersons() {
        ArrayList<Data> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Data person = new Data(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getInt("rent")
                );
                persons.add(person);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching persons: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return persons;
    }
    
    public static int getPersonIdByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT id FROM person WHERE name = ?";
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1; // Not found
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error getting person ID: " + e.getMessage(), 
                                          "Database Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // Don't close the connection here
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage(),
                                             "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static String getPersonNameById(int id) {
        String sql = "SELECT name FROM person WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error getting person name: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null; // Not found
    }
    
    public static String[] getnames() {
        ArrayList<Data> persons = getAllPersons();
        String[] names = new String[persons.size()];
        
        for (int i = 0; i < persons.size(); i++) {
            names[i] = persons.get(i).getName();
        }
        
        return names;
    }
    
    // Database Operations for Bazar
    public static boolean addBazar(Data bazar) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO bazar (date, person_id, list, cost) VALUES (?, ?, ?, ?)";
            conn = DBConnection.getConnection();
            
            // Get person ID without closing the connection
            int personId = getPersonIdByName(bazar.getName());
            if (personId == -1) {
                return false;
            }
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, DBConnection.convertStringToSqlDate(bazar.getDate()));
            pstmt.setInt(2, personId);
            pstmt.setString(3, bazar.getList());
            pstmt.setDouble(4, bazar.getCost());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding bazar: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage(),
                                             "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    public static boolean updateBazar(Data bazar) {
        String sql = "UPDATE bazar SET date = ?, person_id = ?, list = ?, cost = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            int personId = getPersonIdByName(bazar.getName());
            if (personId == -1) {
                return false;
            }
            
            pstmt.setDate(1, DBConnection.convertStringToSqlDate(bazar.getDate()));
            pstmt.setInt(2, personId);
            pstmt.setString(3, bazar.getList());
            pstmt.setDouble(4, bazar.getCost());
            pstmt.setInt(5, bazar.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating bazar: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean deleteBazar(int id) {
        String sql = "DELETE FROM bazar WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting bazar: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static ArrayList<Data> getAllBazar() {
        ArrayList<Data> bazars = new ArrayList<>();
        String sql = "SELECT b.id, b.date, p.name, b.list, b.cost FROM bazar b JOIN person p ON b.person_id = p.id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Data bazar = new Data(
                    rs.getInt("id"),
                    DBConnection.convertSqlDateToString(rs.getDate("date")),
                    rs.getString("name"),
                    rs.getString("list"),
                    rs.getDouble("cost")
                );
                bazars.add(bazar);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching bazar data: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return bazars;
    }
    
    public static ArrayList<Data> getBazarByDate(String date) {
        ArrayList<Data> bazars = new ArrayList<>();
        String sql = "SELECT b.id, b.date, p.name, b.list, b.cost FROM bazar b JOIN person p ON b.person_id = p.id WHERE b.date = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, DBConnection.convertStringToSqlDate(date));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Data bazar = new Data(
                    rs.getInt("id"),
                    DBConnection.convertSqlDateToString(rs.getDate("date")),
                    rs.getString("name"),
                    rs.getString("list"),
                    rs.getDouble("cost")
                );
                bazars.add(bazar);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching bazar by date: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return bazars;
    }
    
    public static double totalBazarcost() {
        String sql = "SELECT SUM(cost) as total FROM bazar";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error calculating total bazar cost: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return 0;
    }
    
    // Database Operations for Meal
    public static boolean addMeal(Data meal) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO meal (date, person_id, meal_count) VALUES (?, ?, ?)";
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            int personId = getPersonIdByName(meal.getName());
            if (personId == -1) {
                return false;
            }
            
            pstmt.setDate(1, DBConnection.convertStringToSqlDate(meal.getDate()));
            pstmt.setInt(2, personId);
            pstmt.setDouble(3, meal.getMealCount());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding meal: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage(),
                                             "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static boolean updateMeal(Data meal) {
        String sql = "UPDATE meal SET date = ?, person_id = ?, meal_count = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            int personId = getPersonIdByName(meal.getName());
            if (personId == -1) {
                return false;
            }
            
            pstmt.setDate(1, DBConnection.convertStringToSqlDate(meal.getDate()));
            pstmt.setInt(2, personId);
            pstmt.setDouble(3, meal.getMealCount());
            pstmt.setInt(4, meal.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating meal: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean deleteMeal(int id) {
        String sql = "DELETE FROM meal WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting meal: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static ArrayList<Data> getAllMeals() {
        ArrayList<Data> meals = new ArrayList<>();
        String sql = "SELECT m.id, m.date, p.name, m.meal_count FROM meal m JOIN person p ON m.person_id = p.id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Data meal = new Data(
                    rs.getInt("id"),
                    DBConnection.convertSqlDateToString(rs.getDate("date")),
                    rs.getString("name"),
                    rs.getDouble("meal_count")
                );
                meals.add(meal);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching meal data: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return meals;
    }
    
    public static ArrayList<Data> getMealsByDate(String date) {
        ArrayList<Data> meals = new ArrayList<>();
        String sql = "SELECT m.id, m.date, p.name, m.meal_count FROM meal m JOIN person p ON m.person_id = p.id WHERE m.date = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, DBConnection.convertStringToSqlDate(date));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Data meal = new Data(
                    rs.getInt("id"),
                    DBConnection.convertSqlDateToString(rs.getDate("date")),
                    rs.getString("name"),
                    rs.getDouble("meal_count")
                );
                meals.add(meal);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching meals by date: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return meals;
    }
    
    public static double totalMeal() {
        String sql = "SELECT SUM(meal_count) as total FROM meal";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error calculating total meal count: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return 0;
    }
    
    public static double totalmealbyname(String name) {
        String sql = "SELECT SUM(m.meal_count) as total FROM meal m JOIN person p ON m.person_id = p.id WHERE p.name = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error calculating total meal by name: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return 0;
    }
    
    // Database Operations for Utility
    public static boolean addUtility(Data utility) {
        String sql = "INSERT INTO utility (date, house_utility, maid_bill, electricity_bill, clean_bill, wifi_bill) VALUES (CURDATE(), ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utility.getHu());
            pstmt.setInt(2, utility.getMb());
            pstmt.setInt(3, utility.getEb());
            pstmt.setInt(4, utility.getCb());
            pstmt.setInt(5, utility.getWb());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding utility: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean updateUtility(Data utility) {
        String sql = "UPDATE utility SET house_utility = ?, maid_bill = ?, electricity_bill = ?, clean_bill = ?, wifi_bill = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utility.getHu());
            pstmt.setInt(2, utility.getMb());
            pstmt.setInt(3, utility.getEb());
            pstmt.setInt(4, utility.getCb());
            pstmt.setInt(5, utility.getWb());
            pstmt.setInt(6, utility.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating utility: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static boolean deleteUtility(int id) {
        String sql = "DELETE FROM utility WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting utility: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static ArrayList<Data> getAllUtilities() {
        ArrayList<Data> utilities = new ArrayList<>();
        String sql = "SELECT * FROM utility ORDER BY date DESC LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Data utility = new Data(
                    rs.getInt("id"),
                    DBConnection.convertSqlDateToString(rs.getDate("date")),
                    rs.getInt("house_utility"),
                    rs.getInt("maid_bill"),
                    rs.getInt("electricity_bill"),
                    rs.getInt("clean_bill"),
                    rs.getInt("wifi_bill")
                );
                utilities.add(utility);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching utility data: " + e.getMessage(), 
                                         "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return utilities;
    }
    
    public static double permeal() {
        double totalBazarCost = totalBazarcost();
        double totalMealCount = totalMeal();
        
        if (totalMealCount > 0) {
            return totalBazarCost / totalMealCount;
        }
        return 0;
    }
    
    public static ArrayList<TotalCost> calculateTotalCost() {
        ArrayList<TotalCost> totalCostList = new ArrayList<>();
        ArrayList<Data> persons = getAllPersons();
        
        double permealCost = permeal();
        double totalUtilityCost = 0;
        
        // Get latest utility costs
        ArrayList<Data> utilities = getAllUtilities();
        if (!utilities.isEmpty()) {
            Data utility = utilities.get(0);
            totalUtilityCost = utility.getHu() + utility.getMb() + utility.getEb() + utility.getCb() + utility.getWb();
        }
        
        double utilityCostPerPerson = totalUtilityCost / persons.size();
        
        for (Data person : persons) {
            String name = person.getName();
            double rentCost = person.getRent();
            double totalmeal = totalmealbyname(name);
            double totalMealCost = totalmeal * permealCost;
            double totalCost = rentCost + utilityCostPerPerson + totalMealCost;
            
            TotalCost totalCostObj = new TotalCost(
                name,
                rentCost,
                utilityCostPerPerson,
                totalmeal,
                String.format("%.2f", permealCost),
                String.format("%.2f", totalMealCost),
                String.format("%.2f", totalCost)
            );
            
            totalCostList.add(totalCostObj);
        }
        
        return totalCostList;
    }
    
    // Static inner class for total cost calculation
    public static class TotalCost {
        private String name;
        private double rentCost;
        private double utilityCostPerPerson;
        private double totalmeal;
        private String permeal;
        private String totalMealCost;
        private String totalCost;
        
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