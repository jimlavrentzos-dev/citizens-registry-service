-- Δημιουργία της βάσης δεδομένων MySQL (αν δεν υπάρχει ήδη),δημιουργία του μη-ριζικού χρήστη και εκχώρηση δικαιωμάτων στο συγκεκριμένο χρήστη 

CREATE DATABASE IF NOT EXISTS citizen_registry_db;

CREATE USER 'registry_user'@'%' IDENTIFIED BY 'secure_password';

GRANT ALL PRIVILEGES ON citizen_registry_db.* TO 'registry_user'@'%';

FLUSH PRIVILEGES;