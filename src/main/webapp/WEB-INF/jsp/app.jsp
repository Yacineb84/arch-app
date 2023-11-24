<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:url var="home" value="/app" />
<c:url var="app" value="/app.js" />



<div id="myApp">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="${home}">Accueil</a> 
		<a class="navbar-brand" href="#" v-if="isLogin" v-on:click="setAddPersonne()">Créer un utilisateur</a> 
		<a class="navbar-brand" href="#" v-on:click="listPersonnes()">Liste des personnes</a>
		<a class="navbar-brand" href="#" v-if="isLogin" v-on:click="getMe()">Mon Profil</a>
		
		
		  <div>
		    <input type="text" id="searchbar_person" name="person" placeholder="Recherche par nom ou prénom.." />
		    <input type="text" id="searchbar_activity" name="activity" placeholder="Recherche par activité.."/>
		    
		    <button 
			class="btn btn-primary btn-sm"
			v-on:click="search_person()">Rechercher</button>
		  </div>
		
		
		<button 
			class="btn btn-primary btn-sm" v-if="!isLogin"
			v-on:click="setLog()">Se connecter</button>
			
		<button 
			class="btn btn-primary btn-sm" v-if="isLogin"
			v-on:click="logout()">Se déconnecter</button>
	</nav>

	<!-- Formulaire de connexion -->

	<div class="container" v-if="(log != null)">
		<form id="app" method="post" novalidate="true">

			<div class="form-group">
				<label>Email :</label> <input v-model="log.email"
					class="form-control" v-bind:class="{'is-invalid':errors.email}" />
			</div>
			<div class="form-group">
				<label>Mot de passe :</label> <input v-model="log.password"
					type="password" class="form-control"
					v-bind:class="{'is-invalid':errors.passsword}" />
			</div>
			<div class="form-group">
				<button v-on:click.prevent="login()" class="btn btn-primary">
					Connexion</button>

			</div>
		</form>
	</div>


	<!-- Liste des personnes -->
	
	<div class="container" v-if="(personne == null && log == null && profil == null)">
		<h1>Liste des personnes</h1>

		<table class="table">
			<tr>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Site</th>
				<th>Date de naissance</th>
				<th>Contact</th>
			</tr>
			<tr v-for="p in personnes">
				<td>{{p.name}}</td>
				<td>{{p.firstName}}</td>
				<td>{{p.site}}</td>
				<td>{{p.dateOfBirth}}</td>
				<td>{{p.email}}</td>
				<td>
					<button class="btn btn-primary btn-sm" v-on:click="showPersonne(p)">Voir CV</button>
				</td>
			</tr>
		</table>
	</div>


	<!-- Affichage d'un CV -->
	
	<div class="container" v-if="(personne != null)">
		<h1>CV de {{personne.name}} {{personne.firstName}}</h1>

		<table class="table">
			<tr>
				<th>Nature</th>
				<th>Année</th>
				<th>Titre</th>
				<th>Description</th>
				<th>Adresse web</th>
			</tr>
			<tr v-for="a in cv.activities">
				<td>{{a.nature}}</td>
				<td>{{a.year}}</td>
				<td>{{a.title}}</td>
				<td>{{a.description}}</td>
				<td>{{a.webAddress}}</td>
			</tr>
		</table>
	</div>
	
	<!-- Affichage de son profil et de son CV -->
	
	<div class="container" v-if="(profil != null)">
		<h1>Mon profil</h1>

		<table class="table">
			<tr>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Site</th>
				<th>Date de naissance</th>
				<th>Contact</th>
			</tr>
			<tr>
				<td>{{profil.name}}</td>
				<td>{{profil.firstName}}</td>
				<td>{{profil.site}}</td>
				<td>{{profil.dateOfBirth}}</td>
				<td>{{profil.email}}</td>
				<td>
					<button style="background-color: green; margin: 5px"
						class="btn btn-primary btn-sm" v-on:click="setEditable(profil)">Editer</button>
				</td>
			</tr>
		</table>
		
		<div class="container" v-if="(editable == null)">
		<h3>Mon CV</h3>

		<table class="table">
			<tr>
				<th>Nature</th>
				<th>Année</th>
				<th>Titre</th>
				<th>Description</th>
				<th>Adresse web</th>
			</tr>
			<tr v-for="a in profil.cv.activities">
				<td>{{a.nature}}</td>
				<td>{{a.year}}</td>
				<td>{{a.title}}</td>
				<td>{{a.description}}</td>
				<td>{{a.webAddress}}</td>
				<td>
				<button style="background-color: red; margin: 5px" v-on:click.prevent="deleteActivity(a)" class="btn btn-primary">
					Supprimer</button>
				</td>
			</tr>
		</table>
		
		<button style="background-color: green; margin: 5px"
						class="btn btn-primary btn-sm" v-on:click="setFormCv()">Ajouter une activité</button>
		
		
		<div class="container" v-if="(formCv != null)">
		<form id="app" method="post" novalidate="true">
		<table>
			<tr>
				<td>
					 <input v-model="formCv.nature"
						class="form-control" v-bind:class="{'is-invalid':errors.nature}" placeholder="Nature" />
					<div v-if="(errors.name)" class="alert alert-warning">
						{{errors.nature}}</div>
				</td>
				<td>
					<input v-model="formCv.year"
						class="form-control" v-bind:class="{'is-invalid':errors.year}"
						number placeholder="Année" />
					<div v-if="(errors.year)" class="alert alert-warning">
						{{errors.year}}</div>
				</td>
				<td>
					<input v-model="formCv.title"
						class="form-control" v-bind:class="{'is-invalid':errors.title}"
						number placeholder="Titre" />
					<div v-if="(errors.title)" class="alert alert-warning">
						{{errors.title}}</div>
				</td>
				<td>
					<input v-model="formCv.description"
						class="form-control" v-bind:class="{'is-invalid':errors.description}"
						number placeholder="Description" />
					<div v-if="(errors.description)" class="alert alert-warning">
						{{errors.description}}</div>
				</td>
				<td>
					<input v-model="formCv.webAddress"
						class="form-control" v-bind:class="{'is-invalid':errors.webAddress}"
						number placeholder="Adresse Web" />
					<div v-if="(errors.webAddress)" class="alert alert-warning">
						{{errors.webAdress}}</div>
				</td>
			</tr>
			</table>
			
			<div class="form-group">
				<button v-on:click.prevent="submitActivity()" class="btn btn-primary">
					Ajouter</button>
				<button style="background-color: red; margin: 5px" v-on:click.prevent="getMe()" class="btn btn-primary">
					Annuler</button>
			</div>
		</form>
	</div>
	
	</div>
		
		
		
		
		
	</div>
	

	<!-- Modification d'une personne -->
	
	<div class="container" v-if="(editable != null)">
		<form id="app" method="post" novalidate="true">

			<div class="form-group">
				<label>Nom :</label> <input v-model="editable.name"
					class="form-control" v-bind:class="{'is-invalid':errors.name}" />
				<div v-if="(errors.name)" class="alert alert-warning">
					{{errors.name}}</div>
			</div>
			<div class="form-group">
				<label>Prénom :</label> <input v-model="editable.firstName"
					class="form-control" v-bind:class="{'is-invalid':errors.year}"
					number />
				<div v-if="(errors.year)" class="alert alert-warning">
					{{errors.year}}</div>
			</div>
			<div class="form-group">
				<label>Site :</label>
				<textarea v-model="editable.site" rows="5" cols="50"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label>Date de naissance :</label>
				<textarea v-model="editable.dateOfBirth" rows="5" cols="50"
					class="form-control"></textarea>
			</div>
			
			<div class="form-group">
				<button v-on:click.prevent="submitPersonne()" class="btn btn-primary">
					Confirmer</button>
				<button style="background-color: red; margin: 5px" v-on:click.prevent="listPersonnes()" class="btn btn-primary">
					Annuler</button>
			</div>
		</form>
	</div>
	
	
	<!-- Création d'un utilisateur -->

	<div class="container" v-if="(addPersonne != null)">
		<form id="app" method="post" novalidate="true">

			<div class="form-group">
				<label>Email :</label> <input v-model="addPersonne.email" class="form-control"
					v-bind:class="{'is-invalid':errors.email}" />
				<div v-if="(errors.email)" class="alert alert-warning">
					{{errors.email}}</div>
			</div>
			<div class="form-group">
				<label>Nom :</label> <input v-model="addPersonne.name" class="form-control"
					v-bind:class="{'is-invalid':errors.name}" number />
				<div v-if="(errors.name)" class="alert alert-warning">
					{{errors.name}}</div>
			</div>
			<div class="form-group">
				<label>Prénom :</label>
				<textarea v-model="addPersonne.firstName"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label>Site :</label>
				<textarea v-model="addPersonne.site"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label>Date de naissance :</label>
				<textarea v-model="addPersonne.dateOfBirth"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label>Mot de passe :</label>
				<textarea v-model="addPersonne.password"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<button v-on:click.prevent="submitAddPersonne()" class="btn btn-primary">
					Créer</button>
				<button v-on:click="listPersonnes()" class="btn btn-primary">
					Annuler</button>
			</div>
		</form>
	</div>


</div>
<script src="${app}" type="module"></script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>