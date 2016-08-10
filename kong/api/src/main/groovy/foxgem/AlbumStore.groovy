package foxgem

class AlbumStore {

    static Map<String, List<String>> albums = [
            '1': ['album1', 'album2', 'album3'],
            '2': ['album4', 'album5']
    ]

    static long countByUser(String userId) {
        albums[userId]?.size() ?: 0
    }

    static List<String> getByUser(String userId) {
        albums[userId]
    }

}
