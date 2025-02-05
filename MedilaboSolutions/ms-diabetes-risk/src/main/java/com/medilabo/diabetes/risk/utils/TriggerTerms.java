package com.medilabo.diabetes.risk.utils;

import java.util.List;

public class TriggerTerms {
	
	public static final List<String> TERMS = List.of(
			"hémoglobine a1c",
			"hemoglobine a1c",
			"microalbumine",
			"taille",
			"poids",
			"fumeur", 
			"fumeuse",
			"anormal",
			"anormale",
			"anormales",
			"cholestérol",
			"cholesterol",
			"vertiges",
			"vertige",
			"rechute",
			"réaction",
			"reaction",
			"anticorps"
	);
	
	//Constructeur privé pour empêcher l'instanciation de cette classe utilitaire
	private TriggerTerms() {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

}
