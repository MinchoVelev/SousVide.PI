"use strict";

function Cooking() {
	this.totalMinutes;
	this.temperature;

	this.startPolling = function() {
		var cooking = this;
		setInterval(
				function() {
					$.get("/api/cooking/status", $.proxy(cooking.pollSuccess,
							cooking));
				}, 1000);
	};
	this.pollSuccess = function(oData) {
		$("#isCooking").html(
				oData.isCooking ? "We're cooking!" : "Not cooking.");

		$("#waterTemperature").html(oData.waterTemperature.toFixed(3) + "");

		if (oData.isCooking) {
			$("#cookingDiv").show();
			$("#startTime").html(new Date(oData.startTime));
			$("#elapsedTime").html(oData.elapsedTime + "");
			var recipeMinutes = oData.curentRecipe.totalMinutes;
			var recipeTemperature = oData.curentRecipe.temperature;
			if (recipeMinutes != this.totalMinutes
					|| recipeTemperature != this.temperature) {
				this.totalMinutes = recipeMinutes;
				$("#inputTotalMinutes").val(this.totalMinutes);
				this.temperature = recipeTemperature;
				$("#inputTemperature").val(this.temperature);
			}

			$("#timebar")
					.width(
							(oData.elapsedTime / (oData.curentRecipe.totalMinutes / 100))
									+ "%");
		} else {
			$("#cookingDiv").hide();
			$("#timebar").width("0%");
		}

	};
	this.refreshRecipe = function() {
		$("#inputTotalMinutes").val(this.totalMinutes);
		$("#inputTemperature").val(this.temperature);
	};
	this.updateRecipe = function() {
		$.get("/api/cooking/status", $.proxy(cooking.pollSuccess, this));
	};
	this.updateRecipe = function() {
		var oRecipe = {
			totalMinutes : $("#inputTotalMinutes").val(),
			temperature : $("#inputTemperature").val()
		};
		$.ajax({
			type : "POST",
			url : "/api/cooking/recipe",
			data : JSON.stringify(oRecipe),
			fail : $.proxy(this.failedPost, this),
			contentType : "application/json"
		});
	};
	this.failedPost = function(oData) {
		alert("Failed to update recipe!")
	}
}