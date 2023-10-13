<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:url var="home" value="/aaa" />
<c:url var="app" value="/app.js" />



<div id="myApp">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="${home}">Films</a> <a
			class="navbar-brand" href="#" v-on:click="populateMovies()">Populate</a>
		<a class="navbar-brand" href="#" v-on:click="setAdd()">Add Movie</a> <a
			class="navbar-brand" href="#" v-on:click="listMovies()">Liste des
			films</a>
		<button class="btn btn-primary btn-sm" v-if="!isLogin"
			v-on:click="setLog()">Login</button>

	</nav>

	<div class="container" v-if="(log != null)">
		<form id="app" method="post" novalidate="true">

			<div class="form-group">
				<label>Username :</label> <input v-model="log.username"
					class="form-control" v-bind:class="{'is-invalid':errors.username}" />
			</div>
				<div class="form-group">
					<label>Password :</label> <input v-model="log.password"
						type="password" class="form-control"
						v-bind:class="{'is-invalid':errors.passsword}" />
				</div>
				<div class="form-group">
					<button v-on:click.prevent="login()" class="btn btn-primary">
						Connexion</button>

				</div>
		</form>
	</div>


	<div class="container" v-if="(movie == null && log == null)">
		<h1>Liste des films</h1>
		<!--
		<message text="Une info" clazz="alert alert-primary"></message>
		<message text="Une alerte" clazz="alert alert-warning"></message>
		<counter ref ="count"> 
			
		</counter> -->

		<table class="table">
			<tr>
				<th>Nom</th>
				<th>Année</th>
				<th>Actions</th>
			</tr>
			<tr v-for="m in movies">
				<td>{{m.name}}</td>
				<td>{{m.year}}</td>
				<td>
					<button class="btn btn-primary btn-sm" v-on:click="showMovie(m)">Montrer</button>
					<button style="background-color: green; margin: 5px"
						class="btn btn-primary btn-sm" v-on:click="setEditable(m)">Editer</button>
					<button style="background-color: red; margin: 5px"
						class="btn btn-primary btn-sm" v-on:click="deleteMovie(m)">Supprimer</button>
				</td>
			</tr>
		</table>
	</div>

	<div class="container" v-if="(movie != null)">
		<h1>Détails d'un film</h1>

		<table class="table">
			<tr>
				<th>Nom :</th>
				<td>{{movie.name}}</td>
			</tr>
			<tr>
				<th>Année :</th>
				<td>{{movie.year}}</td>
			</tr>
			<tr>
				<th>Description :</th>
				<td>{{movie.description}}</td>
				<td></td>
			</tr>
		</table>
	</div>

	<div class="container" v-if="(editable != null)">
		<form id="app" method="post" novalidate="true">

			<div class="form-group">
				<label>Name :</label> <input v-model="editable.name"
					class="form-control" v-bind:class="{'is-invalid':errors.name}" />
				<div v-if="(errors.name)" class="alert alert-warning">
					{{errors.name}}</div>
			</div>
			<div class="form-group">
				<label>Year :</label> <input v-model="editable.year"
					class="form-control" v-bind:class="{'is-invalid':errors.year}"
					number />
				<div v-if="(errors.year)" class="alert alert-warning">
					{{errors.year}}</div>
			</div>
			<div class="form-group">
				<label>Description :</label>
				<textarea v-model="editable.description" rows="5" cols="50"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<button v-on:click.prevent="submitMovie()" class="btn btn-primary">
					Save</button>
				<button v-on:click="listMovies()" class="btn btn-primary">
					Abort</button>
			</div>
		</form>
	</div>

	<div class="container" v-if="(add != null)">
		<form id="app" method="post" novalidate="true">

			<div class="form-group">
				<label>Name :</label> <input v-model="add.name" class="form-control"
					v-bind:class="{'is-invalid':errors.name}" />
				<div v-if="(errors.name)" class="alert alert-warning">
					{{errors.name}}</div>
			</div>
			<div class="form-group">
				<label>Year :</label> <input v-model="add.year" class="form-control"
					v-bind:class="{'is-invalid':errors.year}" number />
				<div v-if="(errors.year)" class="alert alert-warning">
					{{errors.year}}</div>
			</div>
			<div class="form-group">
				<label>Description :</label>
				<textarea v-model="add.description" rows="5" cols="50"
					class="form-control"></textarea>
			</div>
			<div class="form-group">
				<button v-on:click.prevent="addMovie()" class="btn btn-primary">
					Save</button>
				<button v-on:click="listMovies()" class="btn btn-primary">
					Abort</button>
			</div>
		</form>
	</div>


</div>
<script src="${app}" type="module"></script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>