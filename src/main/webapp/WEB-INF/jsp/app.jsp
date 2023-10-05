<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:url var="home" value="/aaa" />
<c:url var="app" value="/app.js" />


<div id="myApp">
    <div class="container" v-if="(movie == null)">
		<h1>Liste des films</h1>

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
					<button  class="btn btn-primary btn-sm" v-on:click="showMovie(m)">Montrer</button>
					<button style="background-color:green;margin:5px" class="btn btn-primary btn-sm" v-on:click="editMovie(m)">Editer</button>
					<button style="background-color:red;margin:5px" class="btn btn-primary btn-sm" v-on:click="deleteMovie(m)">Supprimer</button>
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
	
</div>
<script src="${app}"></script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>