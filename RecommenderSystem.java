import java.io.*;
import java.util.*;
public class RecommenderSystem {
    static class Rating {
        int itemId;
        double rating;
        Rating(int itemId, double rating) {
            this.itemId = itemId;
            this.rating = rating;
        }
    }
    public static void main(String[] args) throws IOException {
        Map<Integer, List<Rating>> userRatings = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("d.csv"));
        String l;
        while ((l= reader.readLine()) != null) {
            String[] parts = l.split(",");
            int userId = Integer.parseInt(parts[0]);
            int itemId = Integer.parseInt(parts[1]);
            double rating = Double.parseDouble(parts[2]);
            userRatings.computeIfAbsent(userId, k -> new ArrayList<>())
                       .add(new Rating(itemId, rating));
        }
        reader.close();
        int tu= 1;
        Set<Integer> targetItems = new HashSet<>();
        if (userRatings.containsKey(tu)) {
            for (Rating r : userRatings.get(tu)) {
                targetItems.add(r.itemId);
            }
        }
        Map<Integer, Double> recommendedScores = new HashMap<>();
        for (int otherUser : userRatings.keySet()) {
            if (otherUser == tu) continue;
            for (Rating r : userRatings.get(otherUser)) {
                if (!targetItems.contains(r.itemId)) {
                    recommendedScores.put(r.itemId,
                        recommendedScores.getOrDefault(r.itemId, 0.0) + r.rating);
                }
            }
        }
        System.out.println("Recommendations for User " + tu + ":");
        for (Map.Entry<Integer, Double> entry : recommendedScores.entrySet()) {
            System.out.println("Item: " + entry.getKey() + ", Score: " + entry.getValue());
        }
    }
}
