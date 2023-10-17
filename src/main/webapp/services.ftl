<#include "base.ftl">

<#macro title>Services</#macro>

<#macro content>

    <script>
        $(document).ready(function () {
            $("#master-select").on("change", function () {
                if (this.value !== -1) {
                    $.get(
                        "${contextPath}/services?master_id=" + this.value,
                        function (response) {
                            let defaultOption = $("<option></option>")
                                .attr("value", "-1")
                                .attr("selected", "")
                                .attr("disabled", "")
                                .text("Choose service")

                            let serviceSelect = $("#service-select")
                            serviceSelect.empty()
                            serviceSelect.append(defaultOption)

                            if (response.length !== 0) {
                                serviceSelect.removeAttr("disabled")
                            } else {
                                serviceSelect.attr("disabled", "")
                            }

                            $.each(response, function (index, service) {
                                let option = $("<option></option>")
                                    .attr("value", service.id)
                                    .text(service.name + " (" + service.duration + " minutes, " + service.price + " RUB)")
                                serviceSelect.append(option)
                            })
                        }
                    )
                } else {
                    $("#submit-button").attr("disabled", "")

                    $("#service-select")
                        .empty()
                        .append(defaultOption)
                        .attr("disabled", "")
                }
            })

            $("#service-select").on("change", function () {
                if (this.value === -1) {
                    $("#submit-button").attr("disabled", "")
                } else {
                    $("#submit-button").removeAttr("disabled")
                }
            })

            $("#phone-input").on("input", function () {
                this.value = (this.value.replaceAll(/\D/g, ""))
            })

            $("#submit-button").on("click", function () {
                $(document).find(".alert").attr("hidden", "")

                let formValid = true

                if ($("#phone-input").val().length != 10) {
                    $("#phone-input").addClass("is-invalid")
                    formValid = false;
                } else {
                    $("#phone-input").removeClass("is-invalid")
                }
                if (!$("#date-input").val()) {
                    $("#date-input").addClass("is-invalid")
                    formValid = false;
                } else {
                    $("#date-input").removeClass("is-invalid")
                }
                if (!$("#time-input").val()) {
                    $("#time-input").addClass("is-invalid")
                    formValid = false;
                } else {
                    $("#time-input").removeClass("is-invalid")
                }
                if ($("#master-select").val() == -1) {
                    $("#master-select").addClass("is-invalid")
                    formValid = false;
                } else {
                    $("#master-select").removeClass("is-invalid")
                }
                if ($("#service-select").val() == -1) {
                    $("#service-select").addClass("is-invalid")
                    formValid = false;
                } else {
                    $("#service-select").removeClass("is-invalid")
                }


                if (formValid) {
                    $("#submit-button").attr("disabled", "true")
                    $("#submit-button-spinner").removeAttr("hidden")
                    $("#submit-button-text").text("Loading...")

                    $.post(
                        "${contextPath}/services",
                        {
                            "masterId": $("#master-select").val(),
                            "serviceId": $("#service-select").val(),
                            "date": $("#date-input").val(),
                            "time": $("#time-input").val(),
                            "phone": $("#phone-input").val()
                        },
                        function (response) {
                            $("#submit-button").removeAttr("disabled")
                            $("#submit-button-spinner").attr("hidden", "true")
                            $("#submit-button-text").text("Submit")

                            if (response.success) {
                                $("#success-alert").removeAttr("hidden")
                            } else if (response.unknownError) {
                                $("#unknown-error-alert").removeAttr("hidden")
                            } else if (response.masterBusy) {
                                $("#master-busy-alert").removeAttr("hidden")
                            }
                        }
                    )
                }
            })
        })
    </script>

    <h1 class="my-3 text-center">Create an appointment</h1>

    <p class="alert alert-success m-3" id="success-alert" hidden>
        <i class="bi bi-check" aria-hidden="true"></i>
        Appointment created!
    </p>

    <p class="alert alert-danger m-3" id="unknown-error-alert" hidden>
        <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
        Unknown error.
    </p>

    <p class="alert alert-danger m-3" id="master-busy-alert" hidden>
        <i class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i>
        Master is busy at chosen time. Please choose another time or date.
    </p>

    <form class="container mt-3">
        <select class="form-select mb-3" id="master-select">
            <option value="-1" selected disabled>Choose master</option>
            <#if masters??>
                <#list masters as master>
                    <option value="${master.id}">${master}</option>
                </#list>
            </#if>
        </select>

        <select class="form-select mb-3" id="service-select" disabled>
            <option value="-1" selected disabled>Choose service</option>
        </select>

        <input type="date" class="form-control mb-3" id="date-input">
        <input type="time" class="form-control mb-3" id="time-input">

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">+7</span>
            </div>
            <input type="tel" maxlength="10" class="form-control"
                    id="phone-input">
        </div>

        <button id="submit-button" type="button" class="btn btn-primary mb-3">
            <span id="submit-button-spinner" class="spinner-border spinner-border-sm" aria-hidden="true" hidden></span>
            <span id="submit-button-text">Submit</span>
        </button>
    </form>

</#macro>
