package cash.z.ecc.android.ext

import cash.z.ecc.android.BuildConfig

object Const {
    /**
     * Named objects for Dependency Injection.
     */
    object Name {
        /** application data other than cryptographic keys */
        const val APP_PREFS = "const.name.app_prefs"
        const val BEFORE_SYNCHRONIZER = "const.name.before_synchronizer"
        const val SYNCHRONIZER = "const.name.synchronizer"
    }

    /**
     * App preference key names.
     */
    object Pref {
        const val FIRST_USE_VIEW_TX = "const.pref.first_use_view_tx"
        const val EASTER_EGG_TRIGGERED_SHIELDING = "const.pref.easter_egg_shielding"
        const val FEEDBACK_ENABLED = "const.pref.feedback_enabled"
        const val SERVER_HOST = "const.pref.server_host"
        const val SERVER_PORT = "const.pref.server_port"
    }

    object ARRRConstants {
        const val DEFAULT_BIRTHDAY_HEIGHT = 1390000
        val anArrayOfBirthdays = arrayListOf<String>(
            "1640000", "1630000", "1620000", "1610000", "1600000",
            "1590000", "1580000", "1570000", "1560000", "1550000", "1540000", "1530000", "1520000", "1510000", "1500000",
            "1490000", "1480000", "1470000", "1460000", "1450000", "1440000", "1430000", "1420000", "1410000", "1400000",
            "1390000", "1380000", "1370000", "1360000", "1350000", "1340000", "1330000", "1320000", "1310000", "1300000",
            "1290000", "1280000", "1270000", "1260000", "1250000", "1240000", "1230000", "1220000", "1210000", "1200000",
            "1190000", "1180000", "1170000", "1160000", "1150000", "1140000", "1130000", "1120000", "1110000", "1100000",
            "1090000", "1080000", "1070000", "1060000", "1050000", "1040000", "1030000", "1020000", "1010000", "1000000",
            "900000", "800000", "700000", "600000", "500000", "400000", "300000", "200000", "Cancel"
        )
    }

    /**
     * Constants used for wallet backup.
     */
    object Backup {
        const val SEED = "cash.z.ecc.android.SEED"
        const val SEED_PHRASE = "cash.z.ecc.android.SEED_PHRASE"
        const val HAS_SEED = "cash.z.ecc.android.HAS_SEED"
        const val HAS_SEED_PHRASE = "cash.z.ecc.android.HAS_SEED_PHRASE"
        const val HAS_BACKUP = "cash.z.ecc.android.HAS_BACKUP"

        // Config
        const val VIEWING_KEY = "cash.z.ecc.android.VIEWING_KEY"
        const val PUBLIC_KEY = "cash.z.ecc.android.PUBLIC_KEY"
        const val BIRTHDAY_HEIGHT = "cash.z.ecc.android.BIRTHDAY_HEIGHT"
    }

    /**
     * Default values to use application-wide. Ideally, this set of values should remain very short.
     */
    object Default {
        object Server {
            // If you've forked the ECC repo, change this to your hosted lightwalletd instance
            const val HOST = BuildConfig.DEFAULT_SERVER_URL
            const val PORT = 443
        }
    }
}
