# Projet android : Hellocine

L’application que nous allons construire contiendra: 

*  des ressources pour afficher et traduire les chaînes de caractères (langue en et fr)
	*  	Creation de strings(fr) et strings(en-rUS)
*   des éléments graphiques de base (bouton, textview, image) 
	*   Bouton, TexteView, ImageView, Menu
*    deux gabarits différents (mode portrait/landscape) pour une des activités 
*     au moins deux activités (et donc au moins deux écrans) 
	*     3 activités : Accueil, Liste de films, Detail
*      des notifications: un toast, une notification dans la barre de notification, une boite dialogue 
	*      Toast : lors de la fin du téléchargement
	*      Notification : lors du clic sur le lien GitHub
	*      Boite de dialogue : le bouton "A propos de nous"
*       l’implémentation d’au moins un bouton dans l’action bar 
	*       Bouton "A propos de nous" 
	*       Recherche
	*       Bouton "retour" sur les details de film
*        un service de téléchargement
	*         Utilisation d'une API pour récupérer les 20 meilleurs films
*         la notification de fin de téléchargement reçu dans un BroadCastReceiver 
	*         Notification lors de la fin du téléchargement
*          le traitement des données téléchargées (JSON) 
	*          Classes : JSONParser et MovieActivity
*           un appel vers une application externe (navigateur, maps, téléphone, autre…) 
	*           Lien vers le navigateur pour le GitHub du projet
*  l’affichage des données téléchargées dans une liste RecyclerView (l’idéal)
	*            Utilisation d'un RecyclerView pour l'affichage des listes des films (avec ImageView)

	
	*Auteur : Maxime FAIVRE et Ludovic CARLU*