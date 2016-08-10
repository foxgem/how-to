package foxgem

class BookStore {

    static Map<String, List<String>> books = [
            '1': ['book1', 'book2', 'book3'],
            '2': ['book4', 'book5']
    ]

    static long countByUser(String userId) {
        books[userId]?.size() ?: 0
    }

    static List<String> getByUser(String userId) {
        books[userId]
    }

    static void addToUser(String userId, String book) {
        if (!books[userId]) {
            books[userId] = []
        }

        books[userId] << book
    }
}
