# Encryption-Decryption
Implementation of Encryption-Decryption project on hyperskill.org

Main work in https://github.com/twjayne/Encryption-Decryption/blob/master/Encryption-Decryption/task/src/encryptdecrypt/Main.java

Program parses command-line arguments with the following options:

-mode: Determine the program's mode (enc - encryption, dec - decryption)

-key:  The number of shifts used to encrypt the message

-data: text or ciphertext within quotes to encrypti or decrypt.

-in: optional to specify input file containing data (should be mutually exclusive with -data).

-out: specify the full name of a file to write the result, assume standard output if none specified.

-alg: algorithm that can be used for encryption or decryption 

  (currently supports "shift" for looping through the alphabets with specified key, and "unicode" for manipulating the ascii value with the specified key)
  
Example:

java Main -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode
