package com.ensta.librarymanager.modele;

public enum Abonnement {
	BASIC("Basic"),PREMIUM("Premium"), VIP("Vip");
	// ce qu'on met dans ("") c'est juste pour un affichage par exemple
	// lorsqu'on écrira Abonnement.BASIC le compilateur comprendra bien "BASIC"
	// tout seul, donc ("Basic") serait juste pour améliorer affichage user

    private final String label;

    Abonnement(String label) {
        this.label = label;
    }
}
