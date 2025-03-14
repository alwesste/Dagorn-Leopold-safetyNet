package com.openclassrooms.safetyNet.result;

import java.util.List;

public class MedicalHistory {
        private List<String> medicine;
        private List<String> allergie;

        public MedicalHistory(List<String> medicine, List<String> allergie) {
            this.medicine = medicine;
            this.allergie = allergie;
        }

        public List<String> getMedicine() {
            return medicine;
        }

        public void setMedicine(List<String> medicine) {
            this.medicine = medicine;
        }

        public List<String> getAllergie() {
            return allergie;
        }

        public void setAllergie(List<String> allergie) {
            this.allergie = allergie;
        }
    }
