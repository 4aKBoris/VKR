@file:Suppress("PackageName")

package mpei.vkr.Constants

val hashAlg = listOf("GOST3411",
    "GOST3411-2012-256",
    "GOST3411-2012-512",
    "KECCAK-224",
    "KECCAK-288",
    "KECCAK-256",
    "KECCAK-384",
    "KECCAK-512",
    "MD2",
    "MD4",
    "MD5",
    "SHA-1",
    "RIPEMD128",
    "RIPEMD160",
    "RIPEMD256",
    "RIPEMD320",
    "SHA-224",
    "SHA-256",
    "SHA-384",
    "SHA-512",
    "SHA-512/224",
    "SHA-512/256",
    "SHA3-224",
    "SHA3-256",
    "SHA3-384",
    "SHA3-512",
    "Skein-256-128",
    "Skein-256-160",
    "Skein-256-224",
    "Skein-256-256",
    "Skein-512-128",
    "Skein-512-160",
    "Skein-512-224",
    "Skein-512-256",
    "Skein-512-384",
    "Skein-512-512",
    "Skein-1024-384",
    "Skein-1024-512",
    "Skein-1024-1024",
    "SM3",
    "TIGER",
    "WHIRLPOOL",
    "BLAKE2B-512",
    "BLAKE2B-384",
    "BLAKE2B-256",
    "BLAKE2B-160",
    "BLAKE2S-256",
    "BLAKE2S-224",
    "BLAKE2S-160",
    "BLAKE2S-128",
    "DSTU7564-256",
    "DSTU7564-384",
    "DSTU7564-512"
)

val keySizeCipher = mapOf(
    Pair("AES", listOf(16, 8, 32)),
    Pair("Blowfish", listOf(1, 1, 56)),
    Pair("DES", listOf(8, 1, 8)),
    Pair("DESede", listOf(24, 1, 24)),
    Pair("RC4", listOf(5, 1, 128)),
    Pair("Camellia", listOf(16, 8, 32)),
    Pair("CAST5", listOf(1, 1, 16)),
    Pair("CAST6", listOf(1, 1, 64)),
    Pair("GOST28147", listOf(32, 1, 32)),
    Pair("IDEA", listOf(1, 1, 128)),
    Pair("Grain128", listOf(16, 1, 128)),
    Pair("Noekeon", listOf(16, 1, 128)),
    Pair("Rijndael", listOf(16, 4, 32)),
    Pair("SEED", listOf(16, 1, 128)),
    Pair("Shacal2", listOf(16, 8, 64)),
    Pair("Serpent", listOf(4, 4, 64)),
    Pair("Skipjack", listOf(10, 1, 128)),
    Pair("SM4", listOf(16, 1, 16)),
    Pair("TEA", listOf(16, 1, 16)),
    Pair("XTEA", listOf(16, 1, 16)),
    Pair("Twofish", listOf(8, 1, 39)),
    Pair("RC2", listOf(1, 1, 128)),
    Pair("RC5", listOf(1, 1, 128)),
    Pair("RC6", listOf(1, 1, 128)),
    Pair("HC128", listOf(16, 1, 16)),
    Pair("HC256", listOf(16, 16, 32)),
    Pair("ChaCha", listOf(16, 16, 32)),
    Pair("Salsa20", listOf(16, 16, 32)),
    Pair("XSalsa20", listOf(32, 1, 32)),
    Pair("VMPC", listOf(1, 1, 128)),
    Pair("Grainv1", listOf(10, 1, 128)),
    Pair("ARIA", listOf(16, 8, 32)),
    Pair("DSTU7624", listOf(16, 16, 32)),
    Pair("GCM", listOf(16, 8, 32)),
    Pair("Threefish-256", listOf(32, 1, 32)),
    Pair("Threefish-512", listOf(64, 1, 64)),
    Pair("Threefish-1024", listOf(128, 1, 128)),
    Pair("GOST3412-2015", listOf(32, 1, 32))
)

val setIV_8 =
    setOf(
        "ChaCha",
        "Salsa20",
        "Grainv1",
        "DES",
        "DESede",
        "Blowfish",
        "XTEA",
        "GOST28147",
        "CAST5",
        "IDEA",
        "Skipjack",
        "TEA",
        "RC2",
        "RC5"
    )
val setIV_12 = setOf("Grain128")
val setIV_16 = setOf(
    "AES",
    "Rijndael",
    "HC128",
    "HC256",
    "Serpent",
    "SM4",
    "Twofish",
    "Camellia",
    "Noekeon",
    "SEED",
    "CAST6",
    "VMPC",
    "ARIA",
    "GCM",
    "RC6",
    "DSTU7624",
    "GOST3412-2015"
)

val setIV_24 = setOf("XSalsa20")
val setIV_32 = setOf("Shacal2", "Threefish-256")
val setIV_64 = setOf("Threefish-512")
val setIV_128 = setOf("Threefish-1024")

val cipherAlg = listOf("AES",
    "Blowfish",
    "DES",
    "DESede",
    "RC4",
    "Camellia",
    "CAST5",
    "CAST6",
    "GOST28147",
    "IDEA",
    "Grain128",
    "Noekeon",
    "Rijndael",
    "SEED",
    "Shacal2",
    "Serpent",
    "Skipjack",
    "SM4",
    "TEA",
    "XTEA",
    "Twofish",
    "RC2",
    "RC5",
    "RC6",
    "HC128",
    "HC256",
    "ChaCha",
    "Salsa20",
    "XSalsa20",
    "VMPC",
    "Grainv1",
    "ARIA",
    "DSTU7624",
    "GCM",
    "Threefish-256",
    "Threefish-512",
    "Threefish-1024",
    "GOST3412-2015")

val cipherBcm = listOf(
    "ECB",
    "CBC",
    "OFB",
    "CFB",
    "CTR",
    "OpenPGPCFB",
    "CTS",
    "GOFB",
    "GCFB",
    "CCM",
    "EAX",
    "GCM",
    "OCB"
)

val AEAD = setOf("CCM", "EAX", "GCM", "OCB")

val cipherPadding = listOf(
    "NoPadding",
    "PKCS5Padding",
    "ISO10126Padding",
    "PKCS7Padding",
    "ISO10126-2Padding",
    "ISO7816-4Padding",
    "ISO9797-1Padding",
    "X923Padding",
    "X9.23Padding",
    "TBCPadding",
    "ZeroBytePadding",
    "withCTS"
)

val cipherStream = setOf(
    "HC128",
    "RC4",
    "HC256",
    "ChaCha",
    "Salsa20",
    "XSalsa20",
    "VMPC",
    "Grainv1",
    "Grain128",
    "Zuc128",
    "Zuc256"
)

val cbc128 = setOf("CCM", "GCM", "OCB")

val cipher64 = setOf(
    "Blowfish",
    "CAST5",
    "DES",
    "DESede",
    "GOST28147",
    "IDEA",
    "RC2",
    "RC5",
    "Skipjack",
    "TEA",
    "XTEA"
)

val cipher128 = setOf(
    "AES",
    "ARIA",
    "Camellia",
    "CAST6",
    "Noekeon",
    "RC5-64",
    "RC-6",
    "Rijndael",
    "SEED",
    "Serpent",
    "SM4",
    "Twofish"
)

val sign = listOf(
    "Не использовать",
    "MD5withRSA",
    "SHA1withRSA",
    "SHA256withDSA",
    "SHA1withECDSA",
    "SHA224withECDSA",
    "SHA256withECDSA",
    "SHA384withECDSA",
    "SHA512withECDSA",
    "SHA224withRSA",
    "SHA256withRSA",
    "SHA384withRSA",
    "SHA512withRSA"
)

var list = ByteArray(128).mapIndexed { index, byte ->  }