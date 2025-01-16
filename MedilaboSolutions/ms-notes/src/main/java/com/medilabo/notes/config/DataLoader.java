package com.medilabo.notes.config;

//@Configuration
public class DataLoader {
	
	/*
	 * Please follow instructions in README file
	 * Enable this class to insert initial data to MongoDB database
	 * Once data has bee successfully insert to database
	 * please disable this class to avoid reinserting data on every app startup
	 * 
	 */
	/*
	@Bean
	public CommandLineRunner loadData(NotesRepository notesRepository) {
		return args -> {
			if (notesRepository.count() == 0) {
				notesRepository.save(new Notes(null, "1","TestNone", "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"));
				notesRepository.save(new Notes(null, "2","TestBorderline", "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"));
				notesRepository.save(new Notes(null, "2","TestBorderline", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"));
				notesRepository.save(new Notes(null, "3","TestInDanger", "Le patient déclare qu'il fume depuis peu"));
				notesRepository.save(new Notes(null, "3","TestInDanger", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"));
				notesRepository.save(new Notes(null, "4","TestEarlyOnset", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"));
				notesRepository.save(new Notes(null, "4","TestEarlyOnset", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"));
				notesRepository.save(new Notes(null, "4","TestEarlyOnset", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"));
				notesRepository.save(new Notes(null, "4","TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction"));
				System.out.println("Notes data loaded into MongoDB.");
			} else {
				System.out.println("Notes data already exists!");
			}
		};
	}
	*/
}
