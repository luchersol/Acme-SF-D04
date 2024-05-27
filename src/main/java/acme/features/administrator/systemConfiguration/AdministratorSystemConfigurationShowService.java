
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.client.services.AbstractService;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	private AdministratorSystemConfigurationRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int administratorId;
		Administrator administrator;

		administratorId = super.getRequest().getPrincipal().getActiveRoleId();
		administrator = this.repository.findOneAdministratorById(administratorId);

		status = administrator != null && super.getRequest().getPrincipal().hasRole(administrator);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.repository.findAllSystemConfigurations().stream().findFirst().orElse(null);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().addData(dataset);
	}

}
