
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

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

		object = this.repository.findAllSystemConfigurations().stream().findFirst().get();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "systemCurrency", "acceptedCurrencies");
	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().addData(dataset);
	}

}
