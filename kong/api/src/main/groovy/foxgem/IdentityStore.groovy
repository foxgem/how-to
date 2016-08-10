package foxgem

class IdentityStore {

    static Map<String, String> root = [id: 'foxgem', password: 'foxgem']
    static Map<String, Map<String, String>> users = [
            '1': [name: 'user1', password: 'user1'],
            '2': [name: 'user2', password: 'user2'],
            '3': [name: 'user3', password: 'user3']
    ]

    static boolean rootExists(String name, String password) {
        root.id == name && root.password == password
    }

    static Map<String, Map<String, String>> findAllUsers() {
        users
    }

    static Map<String, Map<String, String>> getUser(String name, String password) {
        Map.Entry<String, Map<String, String>> entry = users.find { key, value ->
            value.name == name && value.password == password
        }

        if (!entry) {
            [:]
        } else {
            Map<String, Map<String, String>> result = [:]
            result.put(entry.key, entry.value)
            result
        }
    }

}
