# Covidinho
AYATA Enes - FAUGIER Elliot

## Fonctionnalités obligatoires réalisées
  1. Création de compte
  2. Connexion / Déconnexion
  3. Rechercher un utilisateur et l'ajouter en ami
  4. Accepter / Refuser une demande d'ami
  4. Consulter sa liste d'amis
  5. Supprimer un ami (ce dernier est notifié de la suppression)
  5. Consulter les notifications
  6. Supprimer une notification
  7. Se déclarer positif (envoi de notifications aux amis et aux personnes susceptibles d'êtres croisées lors des activités)
  8. Créer/ Déclarer une activitié
  9. Page custom erreur 404
  10. Panel Admin:
      * Accéder à tous les utilisateurs de la plateforme
      * Modifier un utilisateur
      * Supprimer un utilisateur
      * Accéder à toutes les activités de la plateforme
      * Supprimer une activité
      * Modifier une activité 

## Fonctionnalités bonus réalisées
  1. Un utilisateur peut supprimer son propre compte
  2. Un utilisateur peut se déclarer être totalement vacciné ----> les amis sont notifiés de cette information
  3. Un admin ne peut pas supprimer le compte d'un autre admin
  4. Utilisation de datepicker pour le renseignement de la date de naissance d'un utilisateur lors de l'inscription
  5. Utilisation de [https://adresse.data.gouv.fr/api-doc/adresse](https://adresse.data.gouv.fr/api-doc/adresse) pour valider les lieux/adresses lors d'une
  création d'activité.
  6. L’utilisation d’openstreetmap pour afficher géographiquement les lieux et pouvoir les définir lors d'une création d'activité.
  7. L'utilisation du plug-in [Chart.js](https://www.chartjs.org/) et des données fournies par [https://www.gouvernement.fr/info-coronavirus/carte-et-donnees](https://www.gouvernement.fr/info-coronavirus/carte-et-donnees)
  pour afficher, sous forme de graphiques, les différentes statistiques relatives au Covid-19 et à la vaccination contre celui-ci pour nos utilisateurs.

## Stack technique
  * Java 11 avec JEE 8
  * MySQL pour la base de données
  * Javascript et jQuery
  * Bootstrap pour le framework CSS **obligatoire**
  * Tomcat 9
  
  
